package cn.wolfcode.crm.query;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

@Setter
@Getter
public class CustomerQueryObject extends QueryObject {
    private String keyword;
    private Long sellerId = -1L;
    private int status = -1;

    public String getKeyword(){
        return StringUtils.hasLength(keyword) ? keyword.trim() : null;
    }
}
