package cn.wolfcode.crm.domain;

import cn.wolfcode.crm.util.DateUtil;
import cn.wolfcode.crm.util.JsonUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Getter@Setter
public class CustomerTraceHistory extends BaseDomain {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date traceTime;

    private String traceDetails;

    private SystemDictionaryItem traceType;//跟进方式

    //1.优 2.中  3.差
    private Integer traceResult;

    private String remark;

    private Customer customer;

    private Employee inputUser;

    private Date inputTime;
    public String getJsonString(){
        Map<String,Object> map  = new HashMap<>();
        map.put("id",id);
        map.put("customerName",customer.getName());
        map.put("traceTime", DateUtil.formatDate(traceTime));
        map.put("traceDetails",traceDetails);
        map.put("traceType",traceType.getId());
        map.put("traceResult",traceResult);
        map.put("remark",remark);
        return JsonUtil.toJsonString(map);
    }

    public String getTraceResultName(){
        String name =null;
        if(traceResult==1){
            name="优";
        }else if(traceResult==2){
            name="中";
        }else{
            name="差";
        }
        return name;
    }
}