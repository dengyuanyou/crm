package cn.wolfcode.alipay.service.impl;

import cn.wolfcode.alipay.alipayUtils.Alipay;
import cn.wolfcode.alipay.domain.AlipayBean;
import cn.wolfcode.alipay.service.IPayService;
import com.alipay.api.AlipayApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayServiceImpl implements IPayService{

    @Autowired
    private Alipay alipay;

    @Override
    public String aliPay(AlipayBean alipayBean) throws AlipayApiException {
        return alipay.pay(alipayBean);
    }
}
