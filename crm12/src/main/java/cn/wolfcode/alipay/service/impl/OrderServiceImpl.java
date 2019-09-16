package cn.wolfcode.alipay.service.impl;

import cn.wolfcode.alipay.config.AlipayConfig;
import cn.wolfcode.alipay.domain.ContractOrder;
import cn.wolfcode.alipay.mapper.ContractOrderMapper;
import cn.wolfcode.alipay.service.IOrderService;
import com.alipay.api.internal.util.AlipaySignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private ContractOrderMapper contractOrderMapper;

    /**
     * 创建订单
     * @param order
     */
    @Override
    public void createOrder(ContractOrder order) {
        contractOrderMapper.insert(order);
    }

    /**
     * 校验订单
     * 支付宝同步/异步回调时调用
     * @author jitwxs
     * @since 2018/6/4 16:40
     */
    @Override
    public boolean validOrder(Map<String, String> params) throws Exception {
        // 1、调用SDK验证签名
        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, "utf-8", AlipayConfig.sign_type);
        if(!signVerified) {
            return false;
        }
        // 获取订单数据
        String orderId = params.get("out_trade_no");
        ContractOrder orderInfo = contractOrderMapper.selectById(orderId);
        if(orderInfo == null) {
            return false;
        }
        // 2、判断金额是否相等
        String money = params.get("total_amount");
        if(!money.equals(orderInfo.getOrderAmount())) {
            return false;
        }

        // 3、判断商户ID是否相等
        String sellerId = params.get("seller_id");
        if(!sellerId.equals(orderInfo.getCustomerId())) {
            return false;
        }

        // 4、判断APP_ID是否相等
        String appId = params.get("app_id");
        if(!appId.equals(AlipayConfig.app_id)) {
            return false;
        }

        return true;
    }

    /**
     * 更新订单状态
     * @param orderId
     * @param trade_success
     * @param tradeNo
     */
    @Override
    public void changeStatus(String orderId, String trade_success, String tradeNo) {
        contractOrderMapper.changeStatus(orderId,trade_success,tradeNo);
    }
}
