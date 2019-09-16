package cn.wolfcode.crm.query;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

/**
 * 消息模板对象
 */
@Setter
@Getter
public class MessageQueryObject extends QueryObject{

    private String keyword;  //关键字
    private Integer status = 2;  //消息状态

    private Long employeeId;  //销售员工的id

    private String ORDER_BY = "createTime";

    public String getKeyword(){
        return StringUtils.hasLength(keyword) ? keyword.trim() : null;
    }

}
