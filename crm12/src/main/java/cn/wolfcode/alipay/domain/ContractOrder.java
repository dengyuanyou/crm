package cn.wolfcode.alipay.domain;

import cn.wolfcode.crm.domain.BaseDomain;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 合同订单模型对象
 */
@Setter
@Getter
public class ContractOrder extends BaseDomain{

    private String orderName;

    private String orderStatus;

    private String orderAmount;

    private Date createTime;

    private Long customerId;

    private Long contractId;

    private Long alipayId;

}