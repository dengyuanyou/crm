package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.domain.Content;
import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.query.ContractAndContent;
import cn.wolfcode.crm.query.ContractQueryObject;
import cn.wolfcode.crm.service.IContentService;
import cn.wolfcode.crm.service.IContractService;
import cn.wolfcode.crm.service.ICustomerService;
import cn.wolfcode.crm.service.IEmployeeService;
import cn.wolfcode.crm.util.JsonResult;
import cn.wolfcode.crm.util.JsonUtil;
import cn.wolfcode.crm.util.UserContext;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;


/**
 * 合同管理模块
 */

@Controller
@RequestMapping("/contract")
public class ContractController {

    @Autowired
    private IContractService contractService;
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private IContentService contentService;

    /**
     * 合同列表
     * @param model
     * @return
     */
    @RequiresPermissions(value = {"合同列表","contract:list"}, logical = Logical.OR)
    @RequestMapping("/list")
    public String listContract(Model model, @ModelAttribute("qo")ContractQueryObject qo) {

        //获取登录的用户
        Employee userInfo = UserContext.getCurrentEmp();
        if(userInfo.getName().equals("admin")) {
            model.addAttribute("pageInfo",contractService.query(qo));
            //查询除了超级管理员之外的所有销售人员
            model.addAttribute("seller_NotAdmin",employeeService.listSellersNotAdmin());
            //查询状态为潜在客户和客户池的所有客户信息
            model.addAttribute("customers",customerService.listPotentialAndPoolCustomer());

            return "contract/list";

        }else {
            qo.setEmployeeId(userInfo.getId());
            model.addAttribute("pageInfo",contractService.queryMyContract(qo));

            return "contract/myContract";
        }

    }

    /**
     * 合同保存或者编辑
     * @return
     */
    @RequestMapping("/saveOrUpdate")
    @RequiresPermissions(value = {"合同保存或者编辑","contract:saveOrUpdate"}, logical = Logical.OR)
    @ResponseBody
    public JsonResult saveOrUpdate(ContractAndContent contractAndContent) {
        JsonResult result = new JsonResult();
        if(contractAndContent.getContract().getId()!=null) {
            contractService.update(contractAndContent);
        }else {
            contractService.save(contractAndContent);
        }
        return result;
    }

    /**
     * 根据合同内容id查询当前的这个合同的内容明细
     * @param contentId
     * @return
     */
    @RequestMapping("/selectByContentId")
    @ResponseBody
    public String selectByContentId(Long contentId){
        Content content = contentService.selectByContentId(contentId);
        HashMap<String, Object> map = new HashMap<>();
        map.put("id",content.getId());
        map.put("instruction",content.getInstruction());
        return JsonUtil.toJsonString(map);
    }

    /**
     * 修改合同内容
     * @param content
     * @return
     */
    @RequestMapping("/UpdateContent")
    @RequiresPermissions(value = {"修改合同内容","contract:UpdateContent"}, logical = Logical.OR)
    @ResponseBody
    public JsonResult UpdateContent(Content content){
        JsonResult result = new JsonResult();
        if(content.getId()!=null){
            contentService.UpdateContent(content);
        }
        return result;
    }

    /**
     * 删除合同
     * @param contractId
     * @return
     */
    @RequestMapping("/deleteContract")
    @RequiresPermissions(value = {"删除合同","contract:deleteContract"}, logical = Logical.OR)
    @ResponseBody
    public JsonResult deleteContract(Long contractId){
        JsonResult result = new JsonResult();
        if(contractId!=null){
            contractService.deleteContract(contractId);
        }
        return result;
    }

}
