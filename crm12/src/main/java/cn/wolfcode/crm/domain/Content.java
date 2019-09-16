package cn.wolfcode.crm.domain;

import com.alibaba.druid.support.json.JSONUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

/**
 * 合同内容模型对象
 */
@Setter
@Getter
public class Content extends BaseDomain{

    private String instruction;

    //绑定json字符串
    public String getJsonString() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("instruction",instruction);

        return JSONUtils.toJSONString(map);
    }
}