package cn.wolfcode.crm.query;

import cn.wolfcode.crm.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import java.util.Date;

@Setter
@Getter
public class ContractQueryObject extends QueryObject{

    private Long employeeId;  //员工id

    private String keyword;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    public String getKeyword() {
        return StringUtils.hasLength(keyword) ? keyword.trim() : null;
    }

    public Date getEndDate() {
        return DateUtil.getEndDate(endDate);
    }
}
