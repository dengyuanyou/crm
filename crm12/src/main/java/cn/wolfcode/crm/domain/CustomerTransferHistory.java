package cn.wolfcode.crm.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class CustomerTransferHistory extends BaseDomain {

    private Customer customer;

    private Employee operator;

    private Date operateTime;

    private Employee oldSeller;

    private Employee newSeller;

    private String reason;
}