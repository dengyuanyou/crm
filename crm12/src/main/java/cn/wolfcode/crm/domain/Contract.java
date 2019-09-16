package cn.wolfcode.crm.domain;


import com.alibaba.druid.support.json.JSONUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

/**
 * 合同模型对象
 */
@Setter
@Getter
public class Contract extends BaseDomain{

    private Customer customer;  //签约客户

    private Employee seller;    //销售人

    private Long contentId;   //合同内容id

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date inputTime;  //签约日期

    private BigDecimal money;  //合同金额

    //绑定json字符串
    public String getJsonString() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id",id);
        map.put("customerId",customer.id);
        map.put("customerTel",customer.getTel());
        map.put("sellerId",seller.id);
        map.put("sellerEmail",seller.getEmail());
        map.put("contentId",contentId);
        map.put("inputTime",inputTime);
        map.put("money",money);
        map.put("customerName",customer.getName());
        map.put("sellerName",seller.getName());

        return JSONUtils.toJSONString(map);
    }

}