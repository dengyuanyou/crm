package cn.wolfcode.wx.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 微信配置类
 */
@Component
public class WeChatUtil {

    private static Logger logger = Logger.getLogger(WeChatUtil.class);
    private static String token = "dengyuanyou";
    private static String appid = "wxf1ab97ade76f587b";
    private static String appsecret = "5d3aa04dda9ed9aab8c39ec8cc88c4fb";

    public static String accessToken;//调用基础接口的凭据
    public static long expiresTime;//凭据的失效时间

    //发送客户消息接口url
    private static final String REPLY_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=%s";

    //获取基本accessToken接口url
    private static final String GET_ACCESSTOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

    //获取用户基本信息接口url
    private static final String GET_USERINFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s&lang=zh_CN";

    //获取网页用户信息的接口
    public static final String GET_WEB_USERINFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";


    //页面使用jssdk的凭据
    public static final String GET_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";

    //创建菜单的接口
    public static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=%s";

    //网页授权
    public static final String WEB_REDIRECT_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
            "appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_base#wechat_redirect";

    //获取网页授权accessToken的接口
    public static final String GET_WEB_ACCESSTOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";

    //发送模板消息的接口
    public static final String SEND_TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";

    //删除自定义菜单接口
    public static final String DELETE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=%s";

    /**
     * url接入验证逻辑
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    //接口验证
    public static boolean validate(String signature, String timestamp, String nonce) {
        //1）将token、timestamp、nonce三个参数进行字典序排序
        String[] arr = {token, timestamp,nonce};
        Arrays.sort(arr);

        //2）将三个参数字符串拼接成一个字符串进行sha1加密
        StringBuilder sb = new StringBuilder();
        for (String temp : arr) {
            sb.append(temp);
        }

        //自己加密的签名
        String sign = SecurityUtil.SHA1(sb.toString());

        //3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
        return signature.equals(sign);
    }

    /**
     * 删除自定义菜单
     */
    public static void deleteMenuUrl(){

        HttpUtil.get(String.format(DELETE_MENU_URL, getAccesstokenUrl()));
    }

    /**
     * 创建自定义菜单
     */
    public static void createMenu(String menu) {

        HttpUtil.post(String.format(CREATE_MENU_URL, getAccesstokenUrl()), menu);

    }

    /**
     * 创建菜单
     * @param args
     */
    public static void main(String[] args) {
        createMenu("");
    }

    /**
     * 获取微信用户基本信息
     * @return
     */
    public static JSONObject getUserinfoUrl(String openid){
        if(openid!=null){
            String userInfo = HttpUtil.get(String.format(GET_USERINFO_URL, getAccesstokenUrl(), openid));
            return JSON.parseObject(userInfo);
        }
        return null;
    }

    /**
     * 获取access_token凭据
     * @return
     */
    public static String getAccesstokenUrl() {
        if(accessToken == null ||new Date().getTime() >expiresTime){
            //发送请求获取accessToken
            String result = HttpUtil.get(String.format(GET_ACCESSTOKEN_URL, appid, appsecret));
            JSONObject json = JSON.parseObject(result);
            accessToken = json.getString("access_token");
            expiresTime = new Date().getTime() + (json.getLong("expires_in") - 60)*1000;

        }
        return accessToken;
    }

    /**
     * 后台自动回复消息
     */
    public static boolean sendMessage(String openId,String content) {
        //调用发送消息的接口
        Map map =  new HashMap<>();
        Map text = new HashMap();
        text.put("content",content);
        map.put("touser",openId);
        map.put("msgtype","text");
        map.put("text",text);
        HttpUtil.post(String.format(REPLY_URL,getAccesstokenUrl()),map);
        return true;
    }
}
