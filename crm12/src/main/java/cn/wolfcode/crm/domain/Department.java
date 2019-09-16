package cn.wolfcode.crm.domain;

import cn.wolfcode.crm.util.JsonUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class Department extends BaseDomain {
    private String name;
    private String sn;

    public String getJsonString() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("name", name);
        map.put("sn", sn);
        return JsonUtil.toJsonString(map);
    }
}
