package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.domain.Department;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IDepartmentService;
import cn.wolfcode.crm.util.JsonResult;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private IDepartmentService departmentService;

    // /deparment/list
    @RequestMapping("/list")
    /*如果配置多个权限,需要表达出当前方法需要对应的所有的权限还是其中的一个就行*/
    //我们这里必须使用OR:拥有其中一个即可
    @RequiresPermissions(value = {"部门列表","department:list"}, logical = Logical.OR)
    public String list(Model model,@ModelAttribute("qo") QueryObject qo){
        model.addAttribute("pageInfo", departmentService.query(qo));
        return "department/list"; // /WEB-INF/views/department/list.ftl
    }

    @RequestMapping("/delete")
    @RequiresPermissions(value = {"部门删除","department:delete"}, logical = Logical.OR)
    @ResponseBody
    public JsonResult delete(Long id){
        JsonResult result = new JsonResult();
        if(id != null){
            departmentService.delete(id);
        }
        return result;
    }

    // 去新增页面
    @RequestMapping("/input")
    @RequiresPermissions(value = {"部门编辑","department:input"}, logical = Logical.OR)
    public String input(Long id, Model model){
        if(id != null){ // 想去修改
            model.addAttribute("department", departmentService.get(id));
        }
        return "department/input"; // /WEB-INF/views/department/input.ftl
    }

    // 新增保存
    @RequestMapping("/saveOrUpdate")
    @RequiresPermissions(value = {"部门保存或更新","department:saveOrUpdate"}, logical = Logical.OR)
    @ResponseBody
    public JsonResult saveOrUpdate(Department department){
        JsonResult result = new JsonResult();
        if(department.getId() != null){
            departmentService.update(department);
        }else{
            departmentService.save(department);
        }
        return result;

    }

}
