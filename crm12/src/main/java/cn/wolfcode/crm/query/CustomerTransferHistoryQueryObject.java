package cn.wolfcode.crm.query;

import cn.wolfcode.crm.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import java.util.Date;

@Setter
@Getter
public class CustomerTransferHistoryQueryObject extends QueryObject {
    private String keyword;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;

    private Long sellerId;

    public String getKeyword(){
        return StringUtils.hasLength(keyword) ? keyword.trim() : null;
    }

    public Date getEndDate(){
        return DateUtil.getEndDate(endDate);
    }
}
