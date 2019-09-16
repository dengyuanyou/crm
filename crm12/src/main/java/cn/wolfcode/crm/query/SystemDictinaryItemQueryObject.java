package cn.wolfcode.crm.query;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

@Setter
@Getter
public class SystemDictinaryItemQueryObject extends QueryObject {
    private long parentId;

}
