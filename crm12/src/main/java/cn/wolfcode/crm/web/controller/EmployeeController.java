package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.query.EmployeeQueryObject;
import cn.wolfcode.crm.service.IDepartmentService;
import cn.wolfcode.crm.service.IEmployeeService;
import cn.wolfcode.crm.service.IRoleService;
import cn.wolfcode.crm.util.JsonResult;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private IDepartmentService departmentService;
    @Autowired
    private IRoleService roleService;

    @RequestMapping("/list")
    @RequiresPermissions(value = {"员工列表","employee:list"}, logical = Logical.OR)
    public String list(Model model, @ModelAttribute("qo") EmployeeQueryObject qo) {
        model.addAttribute("pageInfo", employeeService.query(qo));
        model.addAttribute("depts", departmentService.listAll());
        return "employee/list";
    }

    @RequestMapping("/delete")
    @RequiresPermissions(value = {"员工删除","employee:delete"}, logical = Logical.OR)
    @ResponseBody
    public JsonResult delete(Long id) {
        JsonResult result = new JsonResult();
        if (id != null) {
            employeeService.delete(id);
        }
        return result;
    }

    // 去新增或修改页面
    @RequestMapping("/input")
    @RequiresPermissions(value = {"员工编辑","employee:input"}, logical = Logical.OR)
    public String input(Long id, Model model) {
        // 把所有部门查询来在页面显示
        model.addAttribute("depts", departmentService.listAll());
        model.addAttribute("roles", roleService.listAll());
        if (id != null) {
            model.addAttribute("employee", employeeService.get(id));
        }
        return "employee/input";
    }

    // 新增保存
    @RequestMapping("/saveOrUpdate")
    @RequiresPermissions(value = {"员工保存或更新","employee:saveOrUpdate"}, logical = Logical.OR)
    @ResponseBody
    public JsonResult saveOrUpdate(Employee employee, Long[] ids) {
        JsonResult result = new JsonResult();
        if (employee.getId() != null) {
            employeeService.update(employee,ids);
        } else {
            employeeService.save(employee,ids);
        }
        return result;
    }

    // 新增保存
    @RequestMapping("/batchDelete")
    @RequiresPermissions(value = {"员工批量删除","employee:batchDelete"}, logical = Logical.OR)
    @ResponseBody
    public JsonResult batchDelete(Long[] ids) {
        JsonResult result = new JsonResult();
        employeeService.batchDelete(ids);
        return result;
    }

    /**
     * 员工报表导出
     * @param qo
     * @param response
     */
    @RequestMapping("/exportExcel")
    @RequiresPermissions(value = {"员工导出","employee:exportExcel"}, logical = Logical.OR)
    public void exportExcel(EmployeeQueryObject qo,HttpServletResponse response) {
        response.setHeader("Content-Disposition","attachment;filename=employee.xls");
        Workbook book = employeeService.exportExcel(qo);
        try {
            book.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 员工表报导入
     * @param xls
     * @return
     */
    @RequestMapping("/importExcel")
    @RequiresPermissions(value = {"员工导入","employee:importExcel"}, logical = Logical.OR)
    public String importExcel(MultipartFile xls) {
        try {
            employeeService.importExcel(xls.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/employee/list.do";
    }


    /**
     * 根据销售人的id查询当前销售人的邮箱
     * @param sellerId
     * @return
     */
    @RequestMapping("/selectEmail")
    @ResponseBody
    public String selectSellerById(Long sellerId) {
        return employeeService.sellerEmailById(sellerId);
    }

}
