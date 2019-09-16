package cn.wolfcode.crm.query;

import cn.wolfcode.crm.domain.Content;
import cn.wolfcode.crm.domain.Contract;
import lombok.Getter;
import lombok.Setter;

/**
 * 合同和合同内容模型对象
 */
@Setter
@Getter
public class ContractAndContent {

    private Contract contract;   //合同对象

    private Content content;    //合同内容对象

}
