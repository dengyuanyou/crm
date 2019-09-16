package cn.wolfcode.wx.vo;

import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.wx.domain.Message;
import lombok.Getter;
import lombok.Setter;

/**
 * 消息和销售员工的模型对象
 */
@Setter
@Getter
public class MessageAndEmployee {

    private Message message;   //消息对象
    private Employee employee;  //员工对象
}
