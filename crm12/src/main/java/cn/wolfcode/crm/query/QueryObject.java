package cn.wolfcode.crm.query;

import lombok.Getter;
import lombok.Setter;
import org.apache.shiro.util.StringUtils;

@Getter
@Setter
public class QueryObject {
    private int currentPage = 1;
    private int pageSize = 5;
    private String orderBy;

    public String getOrderBy() {
        return StringUtils.hasLength(orderBy) ? orderBy : null;
    }

    public int getStart() {
        return (currentPage - 1) * pageSize;
    }
}
