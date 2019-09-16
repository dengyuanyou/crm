package cn.wolfcode.wx.service;

import cn.wolfcode.crm.query.MessageQueryObject;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.wx.domain.Message;
import cn.wolfcode.wx.vo.InMsgEntity;
import cn.wolfcode.wx.vo.MessageAndEmployee;
import cn.wolfcode.wx.vo.OutMsgEntity;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IMessageService {

    /*OutMsgEntity handlerMsg(InMsgEntity inMsg);*/

    String handlerMsg(InMsgEntity inMsg);

    PageInfo<Message> query(MessageQueryObject qo);

    void sendMessage(Message message);

    PageInfo<Message> selectAllOpenid(MessageQueryObject qo);

    void allocation(MessageAndEmployee messageAndEmployee);

    PageInfo<Message> selectByEmployeeIdForMessage(MessageQueryObject qo);

}
