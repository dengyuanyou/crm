package cn.wolfcode.alipay.service;



import cn.wolfcode.alipay.domain.ContractOrder;

import java.util.Map;

public interface IOrderService {

    void createOrder(ContractOrder order);

    boolean validOrder(Map<String, String> params) throws Exception;

    void changeStatus(String orderId, String trade_success, String tradeNo);
}
