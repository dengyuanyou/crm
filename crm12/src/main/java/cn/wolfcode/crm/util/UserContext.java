package cn.wolfcode.crm.util;

import cn.wolfcode.crm.domain.Employee;import org.apache.shiro.SecurityUtils;import org.apache.shiro.subject.Subject;public class UserContext {

    private UserContext(){}

    public static Employee getCurrentEmp(){
        Subject subject = SecurityUtils.getSubject();
        return (Employee) subject.getPrincipal();
    }

    public static boolean isAdminOrSaleManager(){
        Subject subject = SecurityUtils.getSubject();
        Employee emp = getCurrentEmp();
        return emp.isAdmin()||subject.hasRole("CLIENT_MGR");
    }
}
