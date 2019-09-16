package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.domain.Role;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IPermissionService;
import cn.wolfcode.crm.service.IRoleService;
import cn.wolfcode.crm.util.JsonResult;
import cn.wolfcode.crm.util.RequiredPermission;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IPermissionService permissionService;

    /**
     * 角色列表
     * @param model
     * @param qo
     * @return
     */
    @RequestMapping("/list")
    @RequiresPermissions(value = {"角色列表","role:list"}, logical = Logical.OR)
    public String list(Model model,@ModelAttribute("qo") QueryObject qo){
        model.addAttribute("pageInfo", roleService.query(qo));
        return "role/list"; // /WEB-INF/views/role/list.ftl
    }

    /**
     * 角色删除
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @RequiresPermissions(value = {"角色删除","role:delete"}, logical = Logical.OR)
    @ResponseBody
    public JsonResult delete(Long id){
        JsonResult result = new JsonResult();
        if(id != null){
            roleService.delete(id);
        }
        return result;
    }

    // 去新增页面
    @RequestMapping("/input")
    public String input(Long id, Model model){
        if(id != null){ // 想去修改
            model.addAttribute("role", roleService.get(id));
        }
        model.addAttribute("permissions", permissionService.listAll());
        return "role/input"; // /WEB-INF/views/role/input.jsp
    }

    /**
     * 角色新增或者保存
     * @param role
     * @param ids
     * @return
     */
    @RequestMapping("/saveOrUpdate")
    @RequiresPermissions(value = {"角色新增或保存","role:saveOrUpdate"}, logical = Logical.OR)
    @ResponseBody
    public JsonResult saveOrUpdate(Role role,Long[] ids){
        JsonResult result = new JsonResult();
        if(role.getId() != null){
            roleService.update(role,ids);
        }else{
            roleService.save(role,ids);
        }
        return result;
    }

}
