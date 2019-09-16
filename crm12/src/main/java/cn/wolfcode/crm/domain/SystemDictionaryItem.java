package cn.wolfcode.crm.domain;

import cn.wolfcode.crm.util.JsonUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter@Setter
public class SystemDictionaryItem extends BaseDomain {

    private SystemDictionary parent;//当前明细的所属目录

    private String title;

    private Integer sequence;

    public String getJsonString(){
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        map.put("title",title);
        map.put("sequence",sequence);
        map.put("parentTitle",parent.getTitle());

        return JsonUtil.toJsonString(map);
    }

}