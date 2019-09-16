package cn.wolfcode.alipay.config;

import java.io.FileWriter;
import java.io.IOException;

public class AlipayConfig {

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2016092600601897";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDN+xV0bNMfcRROD2857FblCohnZZv7GoVBAUyeUk0Vb4zrE5WGKw25/n/V01D9M140fonAYeU9r1yWnYMAuhHsaPQuGpjkTW6ipFKtMjJ1IrEcL6gXC/L129jLy7AKjos9J7LzHXDq11ni3gvLS49UBppzUDvlOKiwDcO0yeccE64PgBckuZZlN8EyOUN4e1+MDdL6p4mW9pviae2yC/AOcbXLTGtYNLJDQ6vUvX3ZDz3R1rTP90mXiOKZfpmAsSYw1elByO3L0sZiapbLXI3UBwF7BRUT1VpFrNODRppPCj10wLxMSGH2rBOZ6xPkCiD508ktaw5tldx3XKCPirZHAgMBAAECggEAK9nIN4DzcT4hlHy0sSAfX6NeZqS7EDoVJEGijhH3VxjoZyNEMcv4oCAOFvdlxoGpDIPjz5EHvJoYm/fsa7sxFaNCWI5Q/kl9xCI3d6e4FjfMfLOolT91+eFTxg8W6yhtfBd3gUpDP7uY5zNeT2lGmhzNxL7QAXyObOyBPXu+N7uWycweWRuO3o26sxqG2manwizpviZL3ZMHI3jcnAwduGGwW9tLEF0+j9LoSZoj6h7OKoijvb7hLNQVOAbcOfJa2OftJPd/zgdB05Leh5V4IgJi3xZkRirhjRKiLBC1zf2i3TcjBCtBLqymy+yTZ8Sp6YWu1oZolCuadEYzA+6sAQKBgQD3Dmz1glm3BXxlWel97hg8/UeD/BZrw0AZLW/1KB4r3ftTFVfSbcxuc7qVlVWk0pNxZ0IrdaJlnFOpJCl5DTYK8Th7AxnU177X8awBA+t2V3ryqjSkwaKxNjTCKuXxBVY9niEhbcrcqaoYVdy0fk+St16HkahAlkY81m1ugxNT/QKBgQDVb/5udLAqo1E1E8/h7V5l6yp+Wd5v9TN9Anskx2uIN4Tnc/rt1uDx9Mr+PQMyMeIa7IKo8S6+xo+b4Nqgb7C+RW+FfplI/7D/kBI3WUbSW8RV1aKUM2NAsb2o2veGq2bFze0a9IpXBuHhkTSM0n+EBIU/6qMdNOK7OiuJ76oskwKBgQCw66+zkQlWO6N9oQnImLza0DZqmhgkS8qXPSaz1Wrl1y2nrd0aYG7Jmi6/+BVA11unFl8YaEFHqs9ujOr2OTO+WkgsL2hMN8P/OJ4IjBoOwU7WgHT6l4A+j6bwdW/9TekYbykim1Fu/EFG9BX9W4b4dkBHrVNkDTl3T8dGIK+ysQKBgGWiIa6pI+X5xMaYe4nihOvVMnPeCVyYjv78+nSF/T9s6H1qLdepVY4BedPca7QwKrGVY5Zc2lx/ooygYpq49Jl16OfRY95t2vOmv/XZLi8nAXO5+ePGIIIC4QQR2IIpBhQ0va3zOhi8rzzA7xwLdCsCGA1ahnd24mwzA9534Dm/AoGAHJLd3XVuuW7oc5nZWXTLimPdXWLGPyuRzAnljGDtNSM5RJdFCv6pQOegn1bKvyoV7yKwIPXUcRpJ8ttcVg3fHDiC8K5lH3cMXMpsUEpO+tjmT3FwdnKeQBRajSvqnEBTtJP5INZbamPwfdpSs0LbFvz5UCtOgZyPlplhd6UtrCc=";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAh8yPXpDmrS/1doVbrhVBRxPiCtBYUiOOWTmTQllwtCG4TsFI9SGXEhVwc5VWitH0FvY/4UFCeuyaAKqodHvq+u9V3PZ0KaPNBRTj7bsffN1p6kQzZCUUVRR6NrQqFapf4xbqUFQcxoPtHaVHpUCsVT9gEczN8CPNnlIRiUIr2V254Ja7cIv8sBpwukiWGz2/+zKZYCNQT07UQjesSi35/7fLhXqOKgm5lc+yTQxF53sWy8sSkZ4PgYcIbRlEoWk9Wz2m7lb5C63oTCEilEBgfWyvqbhAfqZoxCoL8fxbhSqAD7o9G5Jdilj7vdTorRiKalF4G532UTmEQ2cggcIsYQIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://nhpy3g.natappfree.cc/notify_url";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://nhpy3g.natappfree.cc/return_url.do";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String log_path = "C:\\";


    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
