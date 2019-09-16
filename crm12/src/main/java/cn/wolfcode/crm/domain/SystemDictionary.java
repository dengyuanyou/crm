package cn.wolfcode.crm.domain;

import cn.wolfcode.crm.util.JsonUtil;
import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class SystemDictionary extends BaseDomain {
    private String sn;

    private String title;

    private String intro;

    public String getJsonString(){
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        map.put("sn",sn);
        map.put("title",title);
        map.put("intro",intro);
        return JsonUtil.toJsonString(map);
    }

}