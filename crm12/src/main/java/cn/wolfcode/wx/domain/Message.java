package cn.wolfcode.wx.domain;

import cn.wolfcode.crm.domain.BaseDomain;
import cn.wolfcode.crm.domain.Employee;
import com.alibaba.druid.support.json.JSONUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.HashMap;

/**
 * 微信消息模型对象
 */
@Setter
@Getter
@ToString
public class Message extends BaseDomain {

    private static final Integer REPLAY = 1;  //已回复
    private static final Integer REPLAY_NOT = 2;  //未回复

    private String openid;

    private String content;

    private String type;

    private Integer status = REPLAY_NOT;   //消息的状态

    private Employee employee;   //销售员工id

    private String reply;     //回复内容

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")//后台返回数据给前台,通过设置格式来让
    private Date createTime;

    public String getJsonString() {
        HashMap<String, Object> map = new HashMap();
        map.put("id", id);
        map.put("openid", openid);
        map.put("content",content);
        map.put("status",status);
        map.put("reply",reply);
        return JSONUtils.toJSONString(map);
    }
}