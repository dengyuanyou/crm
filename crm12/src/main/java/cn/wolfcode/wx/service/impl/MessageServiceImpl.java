package cn.wolfcode.wx.service.impl;


import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.query.MessageQueryObject;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.wx.domain.Inform;
import cn.wolfcode.wx.domain.Message;
import cn.wolfcode.wx.mapper.InformMapper;
import cn.wolfcode.wx.mapper.MessageMapper;
import cn.wolfcode.wx.service.IMessageService;
import cn.wolfcode.wx.util.WeChatUtil;
import cn.wolfcode.wx.vo.*;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class MessageServiceImpl implements IMessageService {

    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private InformMapper informMapper;

    //存储消息id的集合
    private static List<Object> temp = new ArrayList<>();


    /**
     * 根据用户输入的关键字被动回复消息
     * @param inMsg
     * @return
     */
    @Override
    public String handlerMsg(InMsgEntity inMsg) {

        //获取微信用户的基本信息
        JSONObject userinfo = WeChatUtil.getUserinfoUrl(inMsg.getFromUserName().toString());
        String nickname = userinfo.getString("nickname");

        Object msgId =  inMsg.getMsgId();

        //判断消息id是否为空,如果为空则是事件
        if(msgId == null) {
           msgId = inMsg.getCreateTime()+inMsg.getFromUserName();
        }

        //判断消息是否存在集合中
        /*if (temp.contains(msgId)) {
            //如果存在就返回null
            return null;
        }*/

        //存储微信用户发送消息的id
        temp.add(inMsg.getMsgId());

        String key = inMsg.getEventKey();

        StringBuffer str = new StringBuffer();

        String msgType = inMsg.getMsgType();

        //如果回复关键字
        if(msgType.equals("text")){
            //如果关键字为服务
            if(inMsg.getContent().contains("服务")){
                //回复公司图文介绍
                str.append("<xml>");
                str.append("<ToUserName><![CDATA[" + inMsg.getFromUserName() + "]]></ToUserName>");
                str.append("<FromUserName><![CDATA[" + inMsg.getToUserName() + "]]></FromUserName>");
                str.append("<CreateTime>" + Calendar.getInstance().getTimeInMillis() / 1000 + "</CreateTime>");
                str.append("<MsgType><![CDATA[news]]></MsgType>");
                str.append("<ArticleCount>"+1+"</ArticleCount>");
                str.append("<Articles><item><Title><![CDATA["+"服务介绍"+"]]></Title>" +
                        "<Description><![CDATA["+"欢迎使用CRM系统"+"]]></Description>" +
                        "<PicUrl><![CDATA[+http://vfk4aa.natappfree.cc/webapp/images/1254.png+]]></PicUrl>" +
                        "<Url><![CDATA[+http://baidu.com+]]></Url></item>" +
                        "</Articles>");
                str.append("</xml>");
            }
            //关注微信公众号发送消息
        }else if(msgType.equals("event")){
            if(inMsg.getEvent().equals("subscribe")){
                str.append("<xml>");
                str.append("<ToUserName><![CDATA[" + inMsg.getFromUserName() + "]]></ToUserName>");
                str.append("<FromUserName><![CDATA[" + inMsg.getToUserName() + "]]></FromUserName>");
                str.append("<CreateTime>" + Calendar.getInstance().getTimeInMillis() / 1000 + "</CreateTime>");
                str.append("<MsgType><![CDATA[text]]></MsgType>");
                str.append("<Content><![CDATA[亲爱的"+nickname+"用户,请输入你需要咨询的服务,会有专门的客服人员回答您的问题哟!]]></Content>");
                str.append("</xml>");
            }
            //如果是CLICK菜单点击事件
            else {
                switch (key){
                    //如果key为1则表示点击公司介绍则回复公司介绍
                    case "1":
                        str.append("<xml>");
                        str.append("<ToUserName><![CDATA[" + inMsg.getFromUserName() + "]]></ToUserName>");
                        str.append("<FromUserName><![CDATA[" + inMsg.getToUserName() + "]]></FromUserName>");
                        str.append("<CreateTime>" + Calendar.getInstance().getTimeInMillis() / 1000 + "</CreateTime>");
                        str.append("<MsgType><![CDATA[text]]></MsgType>");
                        str.append("<Content><![CDATA[我们的公司是一个专门为解决小型客户关系业务提供方案的企业，旗下有...!]]></Content>");
                        str.append("</xml>");
                        break;

                    //如果key为2则表示点击关注我们则回复谢谢支持
                    case "2":
                        str.append("<xml>");
                        str.append("<ToUserName><![CDATA[" + inMsg.getFromUserName() + "]]></ToUserName>");
                        str.append("<FromUserName><![CDATA[" + inMsg.getToUserName() + "]]></FromUserName>");
                        str.append("<CreateTime>" + Calendar.getInstance().getTimeInMillis() / 1000 + "</CreateTime>");
                        str.append("<MsgType><![CDATA[text]]></MsgType>");
                        str.append("<Content><![CDATA[亲爱的"+nickname+",非常感谢您的支持！我们会为您持续提供更好的服务！记得关注呦！]]></Content>");
                        str.append("</xml>");
                        break;
                }
            }

        }
        //如果用户发送的是图片消息则提示用户输入文本
        else if (msgType.equals("image")){
            str.append("<xml>");
            str.append("<ToUserName><![CDATA[" + inMsg.getFromUserName() + "]]></ToUserName>");
            str.append("<FromUserName><![CDATA[" + inMsg.getToUserName() + "]]></FromUserName>");
            str.append("<CreateTime>" + Calendar.getInstance().getTimeInMillis() / 1000 + "</CreateTime>");
            str.append("<MsgType><![CDATA[text]]></MsgType>");
            str.append("<Content><![CDATA[亲爱的"+nickname+"用户,不要输入图片，请输入服务描述!]]></Content>");
            str.append("</xml>");
        }
        //如果用户发送的是声音消息则提示用户输入文本
        else if(msgType.equals("voice")){
            str.append("<xml>");
            str.append("<ToUserName><![CDATA[" + inMsg.getFromUserName() + "]]></ToUserName>");
            str.append("<FromUserName><![CDATA[" + inMsg.getToUserName() + "]]></FromUserName>");
            str.append("<CreateTime>" + Calendar.getInstance().getTimeInMillis() / 1000 + "</CreateTime>");
            str.append("<MsgType><![CDATA[text]]></MsgType>");
            str.append("<Content><![CDATA["+nickname+"用户,不要输入语音，请输入服务描述!]]></Content>");
            str.append("</xml>");
        }

            //根据这条消息的openid查询这个微信用户是否有员工进行跟进
            List<Message> messages = messageMapper.selectByOpenid(inMsg.getFromUserName());

            //保存消息
            Message message = new Message();
            message.setId(inMsg.getMsgId());
            message.setOpenid(inMsg.getFromUserName());
            message.setCreateTime(new Date());
            message.setContent(inMsg.getContent());
            message.setType(inMsg.getMsgType());

            //保存消息通知
            Inform inform = new Inform();
            inform.setId(inMsg.getMsgId());
            inform.setOpenid(inMsg.getFromUserName());
            inform.setCreateTime(new Date());
            inform.setContent(inMsg.getContent());
            inform.setType(inMsg.getMsgType());

            if(inMsg.getMsgType().equals("text")&&!inMsg.getContent().contains("服务")){
                //这里必须只判断message是否为空，而不能判断messages.get(0).getEmployee().getId()为空，因为可能会遇到如果表中一条数据都没有，
                //则会报错，因为message都为空，根本message.get(0)都没有
                if(messages.size()!=0){
                    //将查询出来的employeeId设置到message对象的employeeId上
                    message.setEmployee(messages.get(0).getEmployee());

                    inform.setEmployee(messages.get(0).getEmployee());
                } else {
                    message.setEmployee(null);
                }
                //只有用户输入文本内容时才保存到数据库中
                messageMapper.insert(message);
                //在保存这条消息的同时保存消息通知
                informMapper.insert(inform);

        }
        return str.toString();
    }

    /**
     * 消息后台列表
     * @param qo
     * @return
     */
    @Override
    public PageInfo<Message> query(MessageQueryObject qo) {
        PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());
        List<Message> list = messageMapper.selectForList(qo);
        return new PageInfo(list);

    }


    /**
     * 后台自动回复消息
     * @param message
     */
    @Override
    public void sendMessage(Message message) {

        if(WeChatUtil.sendMessage(message.getOpenid(),message.getContent())){
            //如果回复成功则将此条消息的状态修改为回复成功
            message.setStatus(1);
            messageMapper.updateStatus(message);
            //同时将回复微信客户的信息保存到数据库中
            messageMapper.updatetReplay(message);
        }else {
            //提示发送消息失败
            throw new RuntimeException("回复失败!");
        }
    }

    /**
     * 查询所有的openid
     * @return
     */
    @Override
    public PageInfo<Message> selectAllOpenid(MessageQueryObject qo) {
        PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());//当前页,页面大小
        List<Message> list = messageMapper.selectAllOpenid(qo);
        return new PageInfo<Message>(list);
    }

    /**
     * 为微信客户分配销售员工跟进
     * @param messageAndEmployee
     */
    @Override
    public void allocation(MessageAndEmployee messageAndEmployee) {
        //根据微信客户的openid修改跟踪的员工
        messageMapper.allocation(messageAndEmployee);
    }

    /**
     * 根据当前登录的员工id查询所有的微信客户
     * @param qo
     * @return
     */
    @Override
    public PageInfo<Message> selectByEmployeeIdForMessage(MessageQueryObject qo) {
        PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());//当前页,页面大小
        List<Message> list = messageMapper.selectByEmployeeIdForMessage(qo);
        return new PageInfo<Message>(list);
    }

}
