package cn.wolfcode.crm.web.controller;


import cn.wolfcode.crm.query.MessageQueryObject;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IEmployeeService;
import cn.wolfcode.crm.util.JsonResult;
import cn.wolfcode.crm.util.JsonUtil;
import cn.wolfcode.crm.util.RequiredPermission;
import cn.wolfcode.crm.util.UserContext;
import cn.wolfcode.wx.domain.Inform;
import cn.wolfcode.wx.domain.Message;
import cn.wolfcode.wx.mapper.InformMapper;
import cn.wolfcode.wx.service.IInformService;
import cn.wolfcode.wx.service.IMessageService;
import cn.wolfcode.wx.util.WeChatUtil;
import cn.wolfcode.wx.vo.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.*;
import java.util.List;


/**
 * 微信接入服务
 */
@Controller
@RequestMapping("/wechat")
public class WeChatController {

    @Autowired
    private IMessageService messageService;
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private IInformService iInformService;

    /**
     * 微信接入验证
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @return
     */
    @GetMapping
    @ResponseBody
    public String validate(String signature,String timestamp,String nonce,String echostr){

        if(WeChatUtil.validate(signature,timestamp,nonce)) {
            return echostr;
        }
        return null;
    }

    /**
     * 查询所有的消息列表
     * @return
     */
    @RequiresPermissions(value = {"消息列表","weChat:list"}, logical = Logical.OR)
    @RequestMapping("/list")
    public String listAllMessage(Model model, @ModelAttribute("qo")MessageQueryObject qo){

        model.addAttribute("pageInfo",messageService.query(qo));
        return "weChat/list";
    }

    /**
     * 根据客户的消息回复消息
     * @param message
     * @return
     */
    @PostMapping("/sendMessage")
    @ResponseBody
    public JsonResult autoSendMessage(Message message){
        JsonResult result = new JsonResult();
        if(message.getOpenid() != null) {
            messageService.sendMessage(message);
        }
        return result;
    }


   /* @PostMapping
    @ResponseBody
    public OutMsgEntity sendMessage(@RequestBody  InMsgEntity inMsg){

       return messageService.handlerMsg(inMsg);

    }*/

    /**
     * 根据用户输入关键字自动回复
     * @param inMsg
     * @return
     */
    @PostMapping
    @ResponseBody
    public String sendMessage(@RequestBody  InMsgEntity inMsg){

        return messageService.handlerMsg(inMsg);
    }

    /**
     * 服务分配列表
     * @return
     */
    @RequiresPermissions(value = {"服务分配列表","weChat:distribute"}, logical = Logical.OR)
    @GetMapping("/distribute")
    public String distribute(Model model,@ModelAttribute("qo")MessageQueryObject qo){

        model.addAttribute("pageInfo",messageService.selectAllOpenid(qo));
        //查询所有的销售员工
        model.addAttribute("sellers",employeeService.listSellersNotAdmin());
        return "weChat/distribute";
    }

    /**
     * 为微信客户分配对应的销售员工
     * @return
     */
    @RequiresPermissions(value = {"服务分配","weChat:allocation"}, logical = Logical.OR)
    @RequestMapping("/allocation")
    @ResponseBody
    public JsonResult allocation(MessageAndEmployee messageAndEmployee){
        JsonResult result = new JsonResult();
        if(messageAndEmployee.getMessage().getOpenid()!= null) {
            messageService.allocation(messageAndEmployee);
        }
        return result;
    }

    /**
     * 我的消息列表
     * @return
     */
    @RequestMapping("/myMessage")
    public String myMessage(Model model,@ModelAttribute("qo")MessageQueryObject qo){
            //根据当前登录的销售员工的id查询所有负责的微信客户
            qo.setEmployeeId(UserContext.getCurrentEmp().getId());

            model.addAttribute("pageInfo",messageService.selectByEmployeeIdForMessage(qo));
            return "weChat/myMessage";

    }

    /**
     * 每隔一段时间定时查询消息通知表
     * @return
     */
    @RequestMapping("/inform")
    @ResponseBody
    public String inform(){

        //查询消息通知列表
        List<Inform> informs = iInformService.selectAllInform();
        HashMap<String, Object> map = new HashMap<>();

        //map.put("informs",informs);

        //返回页面列表
        List<Object> list = new ArrayList<>();

        //遍历消息通知列表
        for (Inform inform:informs){
            map.put("id",inform.getId());
            map.put("openid",inform.getOpenid());
            map.put("content",inform.getContent());
            map.put("type",inform.getType());
            map.put("createTime",inform.getCreateTime());
            map.put("status",inform.getStatus());
            map.put("employeeId",inform.getEmployee().getId());
            map.put("reply",inform.getReply());
            list.add(map);
        }

        return JSON.toJSONString(list);
    }

    /**
     * 菜单创建列表
     * @return
     */
    @RequestMapping("/menuList")
    @RequiresPermissions(value = {"菜单创建列表","menu:list"}, logical = Logical.OR)
    public String menuList(Model model){

        //将菜单的类型放到集合中
        List list = new ArrayList<String>();
        list.add("视图菜单");
        list.add("点击菜单");

        //将菜单的类型共享到页面上
        model.addAttribute("MenuList",list);

        return "weChat/menu";

    }

    /**
     * 创建视图菜单
     */
    @RequestMapping("/createViewMenu")
    @ResponseBody
    @RequiresPermissions(value = {"创建视图菜单","menu:viewMenu"}, logical = Logical.OR)
    public JsonResult createViewMenu(ViewButton viewButton){
        JsonResult result = new JsonResult();
        JSONArray button = new JSONArray();
        button.add(viewButton);
        JSONObject menujson = new JSONObject();
        menujson.put("button",button);
        if(viewButton.getName()!=null) {
            WeChatUtil.createMenu(menujson.toJSONString());
        }
        return result;
    }

    /**
     * 创建点击菜单
     * @return
     */
    @RequestMapping("/createClickMenu")
    @ResponseBody
    @RequiresPermissions(value = {"创建点击菜单","menu:clickMenu"}, logical = Logical.OR)
    public JsonResult createClickMenu(ClickButton clickButton){
        JsonResult result = new JsonResult();
        JSONArray button = new JSONArray();
        button.add(clickButton);
        JSONObject menujson = new JSONObject();
        menujson.put("button",button);
        if(clickButton.getName()!=null) {
            WeChatUtil.createMenu(menujson.toJSONString());
        }
        return result;
    }


    /**
     * 删除所有自定义菜单
     * @param args
     */
    public static void main(String[] args) {
        WeChatUtil.deleteMenuUrl();
    }
}
