<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<H1>支付测试</H1>
<hr>
<div class="form-container">
    <form id="form" action="/alipay.do" method="post">
        <#--客户的id和合同的id-->
        <input hidden type="text" name="customer_id" value="${contract.customer.id}">
        <input hidden type="text" name="contract_id" value="${contract.id}">

        *商户订单 :
        <input type="text" name="outTradeNo" value="${outTradeNo}"><br>
        *订单名称 :
        <input type="text" name="subject" value="合同金额"><br>
        *付款金额 :
        <input type="text" name="totalAmount" value="${money}" ><br>
        *商品描述 :
        <input type="text" name="body" value="${content.instruction}"><br>
        <input type="button" value="支付宝支付" onclick="submitForm('/alipay.do')">
        <input type="button" value=" 微信支付  " onclick="submitForm('order/wexpay')">
    </form>
</div>
</body>

<script language="javascript">
    function submitForm(action) {
        document.getElementById("form").action = action
        document.getElementById("form").submit()
    }
</script>

<style>
    .form-container {
        padding-top:10px;
    }
    input {
        margin:10px;

    }
</style>
</html>