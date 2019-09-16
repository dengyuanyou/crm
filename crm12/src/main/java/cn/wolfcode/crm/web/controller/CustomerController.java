package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.domain.Customer;
import cn.wolfcode.crm.domain.CustomerTransferHistory;
import cn.wolfcode.crm.domain.Employee;import cn.wolfcode.crm.query.CustomerQueryObject;
import cn.wolfcode.crm.service.ICustomerService;
import cn.wolfcode.crm.service.IEmployeeService;
import cn.wolfcode.crm.service.ISystemDictionaryItemService;import cn.wolfcode.crm.util.JsonResult;
import cn.wolfcode.crm.util.UserContext;
import com.alibaba.druid.support.json.JSONUtils;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import net.sf.json.JSONArray;
import org.apache.shiro.SecurityUtils;import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//潜在客户/流失/客户池...
@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private ISystemDictionaryItemService itemService;

    /**
     * 查询客户列表
     * @param model
     * @param qo
     * @return
     */
    @RequestMapping("/list")
    @RequiresPermissions(value = {"客户列表","customer:list"}, logical = Logical.OR)
    public String CustomerList(Model model,@ModelAttribute("qo") CustomerQueryObject qo) {
        //查询所有的客户列表
        model.addAttribute("pageInfo", customerService.queryAll(qo));
        //查询所有的营销人员
        model.addAttribute("sellers", employeeService.listAllSellers());
        return "customer/list";
    }

    @RequestMapping("/potentialList")
    @RequiresPermissions(value = {"潜在客户列表","potentialCustomer:list"}, logical = Logical.OR)
    public String list(Model model,@ModelAttribute("qo") CustomerQueryObject qo){
        //根据用户的身份进行查询
        //如果是超管或者是营销经理,查询所有
        Employee emp = UserContext.getCurrentEmp();
        if(!UserContext.isAdminOrSaleManager()){
            qo.setSellerId(emp.getId());
        }

        //查询所有的潜在客户
        qo.setStatus(Customer.STATUS_POTENTIAL);
        model.addAttribute("pageInfo", customerService.query(qo));
        //查询所有的营销人员
        model.addAttribute("sellers", employeeService.listAllSellers());


        //查询所有的职业
        model.addAttribute("jobs",itemService.listJobs());
        //查询所有的来源
        model.addAttribute("sources",itemService.listSources());
        //查询所有的交流方式
        model.addAttribute("types",itemService.listTypes());


        return "customer/potentialList";
    }

    @RequestMapping("/input")
    @RequiresPermissions(value = {"潜在客户编辑","potentialCustomer:input"}, logical = Logical.OR)
    public String input(Long id, Model model){
        if(id != null){
            model.addAttribute("customer", customerService.get(id));
        }
        return "customer/input";
    }

    @RequestMapping("/saveOrUpdate")
    @RequiresPermissions(value = {"潜在客户保存或更新","potentialCustomer:saveOrUpdate"}, logical = Logical.OR)
    @ResponseBody
    public JsonResult saveOrUpdate(Customer customer){
        JsonResult result = new JsonResult();
        if(customer.getId() != null){
            customerService.update(customer);
        }else{
            customerService.save(customer);
        }
        return result;

    }


    @RequestMapping("/poolList")
    @RequiresPermissions(value = {"客户池列表","customerPool:list"}, logical = Logical.OR)
    public String poolList(Model model,@ModelAttribute("qo") CustomerQueryObject qo){
        //查询所有的客户池的客户
        qo.setStatus(Customer.STATUS_POOLED);
        model.addAttribute("pageInfo", customerService.query(qo));

        //查询所有的营销人员
        model.addAttribute("sellers", employeeService.listAllSellers());
        return "customer/poolList";
    }

    @RequestMapping("/updateStatus")
    @RequiresPermissions(value = {"修改潜在客户状态","customerPool:updateStatus"}, logical = Logical.OR)
    public String updateStatus(Long cid,Integer status){
        customerService.updateStatus(cid,status);
        return "redirect:/customer/potentialList.do";
    }

    /**
     * 失败客户列表
     * @return
     */
    @RequestMapping("/failList")
    @RequiresPermissions(value = {"失败客户列表","failCustomer:list"}, logical = Logical.OR)
    public String failList(Model model,@ModelAttribute("qo") CustomerQueryObject qo){

        //设置客户的状态为开发失败
        qo.setStatus(4);
        qo.setSellerId(1L);
        model.addAttribute("pageInfo",customerService.failList(qo));
        //查询所有的营销人员
        model.addAttribute("sellers", employeeService.listAllSellers());
        //获取除了管理员的其他所有销售人员
        model.addAttribute("seller_NotAdmin",employeeService.listSellersNotAdmin());
        return "customer/failList";
    }

    /**
     * 失败客户跟进
     * @return
     */
    @RequestMapping("/failTransfer")
    @RequiresPermissions(value = {"失败客户跟进","failCustomer:failTransfer"}, logical = Logical.OR)
    @ResponseBody
    public JsonResult failTransfer(Customer customer) {
        JsonResult result = new JsonResult();
        try {
            customerService.failTransfer(customer);
        }catch (RuntimeException e) {
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 失败客户分配
     * @return
     */
    @RequestMapping("/distribute")
    @RequiresPermissions(value = {"失败客户分配","failCustomer:distribute"}, logical = Logical.OR)
    public String distribute(CustomerTransferHistory customerTransferHistory) {

        customerService.distribute(customerTransferHistory);
        //重定向到潜在客户列表页面
        return "redirect:/customer/potentialList.do";
    }

    /**
     * 查询除了超级管理员和旧的销售人员的所有销售人员
     * @param selledId
     * @return
     */
    @RequestMapping(value = "/selectSellers",method = RequestMethod.GET)
    @ResponseBody
    public String selectSellers(Model model,Long selledId) {
        List<Employee> sellers = employeeService.selectSellers(selledId);
        /*model.addAttribute("AllSellers",sellers);*/
       /* return "customer/failList";*/
        //将list集合转化成json字符串
       /* ArrayList<Employee> list = new ArrayList<>();
        for (Employee e:sellers) {
                list.add(e);
        }*/
        return JSONArray.fromObject(sellers).toString();

    }

    /**
     * 根据客户id查询这个客户信息
     * @param tel
     * @return
     */
    @RequestMapping("/selectCustomerInfo")
    @ResponseBody
    public String selectCustomerInfo(Long tel) {
        Customer customer = customerService.selectCustomerInfo(tel);
        HashMap<String, Object> map = new HashMap<>();
        map.put("id",customer.getId());
        map.put("name",customer.getName());
        map.put("tel",customer.getTel());
        map.put("seller_id",customer.getSeller().getId());
        map.put("seller_name",customer.getSeller().getName());
        map.put("seller_email",customer.getSeller().getEmail());
        return JSONUtils.toJSONString(map);
    }

}
