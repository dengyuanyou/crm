package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.domain.CustomerTransferHistory;
import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.query.CustomerTransferHistoryQueryObject;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.ICustomerTransferHistoryService;
import cn.wolfcode.crm.service.IEmployeeService;
import cn.wolfcode.crm.service.ISystemDictionaryItemService;
import cn.wolfcode.crm.util.UserContext;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customerTransferHistory")
public class CustomerTransferHistoryController {

    @Autowired
    private ICustomerTransferHistoryService customerTransferHistoryService;
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private ISystemDictionaryItemService itemService;

    @RequestMapping("/list")
    @RequiresPermissions(value = {"移交历史列表", "customerTransferHistory:list"}, logical = Logical.OR)
    public String list(Model model, @ModelAttribute("qo") CustomerTransferHistoryQueryObject qo) {
        Employee emp = UserContext.getCurrentEmp();
        if(!UserContext.isAdminOrSaleManager()){
            qo.setSellerId(emp.getId());
        }
        model.addAttribute("pageInfo", customerTransferHistoryService.query(qo));
        return "customerTransferHistory/list";
    }

    @RequestMapping("/save")
    @RequiresPermissions(value = {"潜在客户移交", "customerTransferHistory:save"}, logical = Logical.OR)
    public String save(CustomerTransferHistory customerTransferHistory) {
        customerTransferHistoryService.save(customerTransferHistory);
        return "redirect:/customerTransferHistory/list.do";

    }

}
