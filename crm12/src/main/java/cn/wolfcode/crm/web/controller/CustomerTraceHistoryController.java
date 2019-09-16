package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.domain.CustomerTraceHistory;
import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.query.CustomerTraceHistoryQueryObject;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.ICustomerTraceHistoryService;
import cn.wolfcode.crm.service.IEmployeeService;
import cn.wolfcode.crm.service.ISystemDictionaryItemService;
import cn.wolfcode.crm.util.JsonResult;
import cn.wolfcode.crm.util.UserContext;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/customerTraceHistory")
public class CustomerTraceHistoryController {

    @Autowired
    private ICustomerTraceHistoryService customerTraceHistoryService;
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private ISystemDictionaryItemService itemService;

    // /deparment/list
    @RequestMapping("/list")
    @RequiresPermissions(value = {"跟进历史列表","customerTraceHistory:list"}, logical = Logical.OR)
    public String list(Model model,@ModelAttribute("qo") CustomerTraceHistoryQueryObject qo){
        //根据用户的身份进行查询
        //如果是超管或者是营销经理,查询所有
        Employee emp = UserContext.getCurrentEmp();
        if(!UserContext.isAdminOrSaleManager()){
            qo.setSellerId(emp.getId());
        }

        //按照跟进时间做降序排序
        qo.setOrderBy("his.trace_time DESC");
        model.addAttribute("pageInfo", customerTraceHistoryService.query(qo));

        //查询所有的交流方式
        model.addAttribute("types",itemService.listTypes());
        return "customerTraceHistory/list";
    }

    @RequestMapping("/saveOrUpdate")
    @RequiresPermissions(value = {"跟进历史保存或更新","customerTraceHistory:saveOrUpdate"}, logical = Logical.OR)
    public String saveOrUpdate(CustomerTraceHistory customerTraceHistory){
        if(customerTraceHistory.getId() != null){
            customerTraceHistoryService.update(customerTraceHistory);
        }else{
            customerTraceHistoryService.save(customerTraceHistory);
        }
        return "redirect:/customerTraceHistory/list.do";

    }

}
