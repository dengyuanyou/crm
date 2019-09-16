package cn.wolfcode.wx.domain;

import cn.wolfcode.crm.domain.BaseDomain;
import cn.wolfcode.crm.domain.Employee;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 通知模型对象
 */
@Setter
@Getter
@ToString
public class Inform extends BaseDomain{

    private static final Integer REPLAY = 1;  //已回复
    private static final Integer REPLAY_NOT = 2;  //未回复

    private String openid;   //消息id

    private String content;   //消息内容

    private String type;      //消息类型

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")//后台返回数据给前台,通过设置格式来让
    private Date createTime;   //发送时间

    private Integer status = REPLAY_NOT;    //消息状态

    private Employee employee;   //销售员工id

    private String reply;  //回复内容

}