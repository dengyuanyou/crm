package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IPermissionService;
import cn.wolfcode.crm.util.JsonResult;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private IPermissionService permissionService;

    /**
     * 权限列表
     * @param model
     * @param qo
     * @return
     */
    @RequestMapping("/list")
    @RequiresPermissions(value = {"权限列表","permission:list"}, logical = Logical.OR)
    public String list(Model model,@ModelAttribute("qo") QueryObject qo){
        model.addAttribute("pageInfo", permissionService.query(qo));
        return "permission/list";
    }

    /**
     * 权限删除
     * @param id
     * @return
     */
    @RequiresPermissions(value = {"权限删除","permission:delete"}, logical = Logical.OR)
    @RequestMapping("/delete")
    @ResponseBody
    public JsonResult delete(Long id){
        JsonResult result = new JsonResult();
        if(id != null){
            permissionService.delete(id);
        }
        return result;
    }

    /**
     * 加载权限
     * @return
     */
    @RequestMapping("/load")
    @RequiresPermissions(value = {"加载权限","permission:load"}, logical = Logical.OR)
    @ResponseBody
    public JsonResult load(){
        JsonResult result = new JsonResult();
        permissionService.load();
        return result;
    }

}
