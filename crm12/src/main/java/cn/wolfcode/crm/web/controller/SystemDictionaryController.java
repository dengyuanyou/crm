package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.domain.SystemDictionary;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.ISystemDictionaryService;
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
@RequestMapping("/systemDictionary")
public class SystemDictionaryController {

    @Autowired
    private ISystemDictionaryService systemDictionaryService;

    @RequestMapping("/list")
    @RequiresPermissions(value = {"字典目录列表","systemDictionary:list"}, logical = Logical.OR)
    public String list(Model model,@ModelAttribute("qo") QueryObject qo){
        model.addAttribute("pageInfo", systemDictionaryService.query(qo));
        return "systemDictionary/list";
    }

    @RequestMapping("/delete")
    @RequiresPermissions(value = {"字典目录删除","systemDictionary:delete"}, logical = Logical.OR)
    @ResponseBody
    public JsonResult delete(Long id){
        JsonResult result = new JsonResult();
        if(id != null){
            systemDictionaryService.delete(id);
        }
        return result;
    }

    @RequestMapping("/input")
    @RequiresPermissions(value = {"字典目录编辑","systemDictionary:input"}, logical = Logical.OR)

    public String input(Long id, Model model){
        if(id != null){
            model.addAttribute("systemDictionary", systemDictionaryService.get(id));
        }
        return "systemDictionary/input";
    }

    @RequestMapping("/saveOrUpdate")
    @RequiresPermissions(value = {"字典目录保存或更新","systemDictionary:saveOrUpdate"}, logical = Logical.OR)
    @ResponseBody
    public JsonResult saveOrUpdate(SystemDictionary systemDictionary){
        JsonResult result = new JsonResult();
        if(systemDictionary.getId() != null){
            systemDictionaryService.update(systemDictionary);
        }else{
            systemDictionaryService.save(systemDictionary);
        }
        return result;

    }

}
