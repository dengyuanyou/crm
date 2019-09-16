package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.domain.SystemDictionaryItem;
import cn.wolfcode.crm.query.SystemDictinaryItemQueryObject;
import cn.wolfcode.crm.service.ISystemDictionaryItemService;
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
@RequestMapping("/systemDictionaryItem")
public class SystemDictionaryItemController {

    @Autowired
    private ISystemDictionaryItemService systemDictionaryItemService;
    @Autowired
    private ISystemDictionaryService systemDictionaryService;

    @RequestMapping("/list")
    @RequiresPermissions(value = {"字典明细列表","systemDictionaryItem:list"}, logical = Logical.OR)
    public String list(Model model,@ModelAttribute("qo") SystemDictinaryItemQueryObject qo){
        qo.setOrderBy("item.sequence DESC");
        model.addAttribute("pageInfo", systemDictionaryItemService.query(qo));
        model.addAttribute("parent", systemDictionaryService.get(qo.getParentId()));

        model.addAttribute("dictinaries", systemDictionaryService.listAll());
        return "systemDictionaryItem/list";
    }

    @RequestMapping("/delete")
    @RequiresPermissions(value = {"字典明细删除","systemDictionaryItem:delete"}, logical = Logical.OR)
    @ResponseBody
    public JsonResult delete(Long id){
        JsonResult result = new JsonResult();
        if(id != null){
            systemDictionaryItemService.delete(id);
        }
        return result;
    }

    @RequestMapping("/input")
    @RequiresPermissions(value = {"字典明细编辑","systemDictionaryItem:input"}, logical = Logical.OR)
    public String input(Long id, Model model){
        if(id != null){
            model.addAttribute("systemDictionaryItem", systemDictionaryItemService.get(id));
        }
        return "systemDictionaryItem/input";
    }

    @RequestMapping("/saveOrUpdate")
    @RequiresPermissions(value = {"字典明细保存或更新","systemDictionaryItem:saveOrUpdate"}, logical = Logical.OR)
    @ResponseBody
    public JsonResult saveOrUpdate(SystemDictionaryItem systemDictionaryItem){
        JsonResult result = new JsonResult();
        if(systemDictionaryItem.getId() != null){
            systemDictionaryItemService.update(systemDictionaryItem);
        }else{
            systemDictionaryItemService.save(systemDictionaryItem);
        }
        return result;

    }

}
