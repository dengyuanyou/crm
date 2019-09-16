package cn.wolfcode.alipay.web.controller;

import cn.wolfcode.alipay.domain.AlipayBean;
import cn.wolfcode.alipay.domain.ContractOrder;
import cn.wolfcode.alipay.service.IOrderService;
import cn.wolfcode.alipay.service.IPayService;
import cn.wolfcode.crm.domain.Content;
import cn.wolfcode.crm.domain.Contract;
import cn.wolfcode.crm.service.IContentService;
import cn.wolfcode.crm.service.IContractService;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;


/**
 * 合同订单业务相关
 */
@Controller
public class OrderController {

    @Autowired
    private IPayService payService;
    @Autowired
    private IContractService contractService;
    @Autowired
    private IContentService contentService;
    @Autowired
    private IOrderService orderService;

    /**
     * 阿里支付
     * @param
     * @param subject
     * @param
     * @param body
     * @return
     * @throws AlipayApiException
     */
    @PostMapping(value = "alipay")
    @ResponseBody
    public String alipay(String outTradeNo, String subject, String totalAmount, String body, String customer_id, String contract_id) throws AlipayApiException {
        //创建订单对象
        ContractOrder order = new ContractOrder();
        //生成订单
        order.setOrderName(subject);
        order.setOrderAmount(totalAmount);
        order.setCreateTime(new Date());
        order.setCustomerId(Long.parseLong(customer_id));
        order.setContractId(Long.parseLong(contract_id));
        //保存订单
        orderService.createOrder(order);

        AlipayBean alipayBean = new AlipayBean();
        alipayBean.setOut_trade_no(outTradeNo);
        alipayBean.setSubject(subject);
        alipayBean.setTotal_amount(totalAmount);
        alipayBean.setBody(body);
        return payService.aliPay(alipayBean);
    }

    /**
     * 支付宝支付完之后的回调地址
     * @param
     * @param response
     * @throws Exception
     */
    @RequestMapping("/return_url")
    public void returnUrl(@RequestParam Map<String,String> params, HttpServletResponse response) throws Exception {

        response.setContentType("text/html;charset=utf-8");

        try {
            //验证订单
            boolean flag =  orderService.validOrder(params);

            if(flag) {
                // 验证成功后，修改订单状态为已支付
                String orderId = params.get("out_trade_no");
                /*
                 * 订单状态（与官方统一）
                 * WAIT_BUYER_PAY：交易创建，等待买家付款；
                 * TRADE_CLOSED：未付款交易超时关闭，或支付完成后全额退款；
                 * TRADE_SUCCESS：交易支付成功；
                 * TRADE_FINISHED：交易结束，不可退款
                 */
                // 获取支付宝订单号
                String tradeNo = params.get("trade_no");
                // 更新订单状态
                orderService.changeStatus(orderId, "TRADE_SUCCESS", tradeNo);

                response.setContentType("text/html;charset=utf-8");
                response.getWriter().write("<!DOCTYPE html>\n" +
                        "<html lang=\"en\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <title>支付成功</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "\n" +
                        "<div class=\"container\">\n" +
                        "    <div class=\"row\">\n" +
                        "        <p>订单号："+orderId+"</p>\n" +
                        "        <p>支付宝交易号："+tradeNo+"</p>\n" +
                        "        <a href=\"/\">返回首页</a>\n" +
                        "    </div>\n" +
                        "</div>\n" +
                        "\n" +
                        "</body>\n" +
                        "</html>");
            } else {
                response.getWriter().write("支付验证失败");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 根据合同id当前的合同对象
     * @return
     */
    @RequestMapping("/getContract")
    public String getContract(Model model,Long id){

        //根据合同id查询当前的合同对象
        Contract contract = contractService.getContract(id);

        //根据合同内容id查询合同内容对象
        Content content = contentService.selectByContentId(contract.getContentId());

        //将金额转化成字符串
        String money = contract.getMoney()+"";
        model.addAttribute("money",money);

        //将合同内容对象共享到页面上
        model.addAttribute("content",content);

        //共享到页面上
        model.addAttribute("contract",contract);

        //随机生成商品订单号
        int r1=(int)(Math.random()*(10));//产生2个0-9的随机数
        int r2=(int)(Math.random()*(10));
        long now = System.currentTimeMillis();//一个13位的时间戳
        String paymentID =String.valueOf(r1)+String.valueOf(r2)+String.valueOf(now);// 订单ID

        model.addAttribute("outTradeNo",paymentID);

        return "contract/orderList";
    }



}
