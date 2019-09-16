package cn.wolfcode.alipay.mapper;

import cn.wolfcode.alipay.domain.ContractOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ContractOrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ContractOrder record);

    ContractOrder selectByPrimaryKey(Long id);

    List<ContractOrder> selectAll();

    int updateByPrimaryKey(ContractOrder record);

    ContractOrder selectById(String orderId);

    void changeStatus(@Param("orderId") String orderId, @Param("trade_success") String trade_success, @Param("tradeNo") String tradeNo);
}