package cn.wolfcode.wx.mapper;

import cn.wolfcode.crm.query.MessageQueryObject;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.wx.domain.Message;
import cn.wolfcode.wx.vo.MessageAndEmployee;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MessageMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Message record);

    Message selectByPrimaryKey(Long id);

    List<Message> selectAll();

    int updateByPrimaryKey(Message record);

    List<Message> selectForList(MessageQueryObject qo);

    void updateStatus(Message message);

    List<Message> selectAllOpenid(MessageQueryObject qo);

    void allocation(MessageAndEmployee messageAndEmployee);

    List<Message> selectByOpenid(String openid);

    void updatetReplay(Message message);

    List<Message> selectByEmployeeIdForMessage(MessageQueryObject qo);

    Message selectListFortMessage(@Param("messageId") Long messageId, @Param("employeeId") Long employeeId);
}