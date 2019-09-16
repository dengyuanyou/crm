package cn.wolfcode.wx.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Setter
@Getter
@XmlRootElement(name="xml")
@XmlAccessorType(XmlAccessType.FIELD)
@ToString
public class InMsgEntity {
    private String ToUserName;   //  开发者微信号
    private String FromUserName;   // 发送方帐号（一个OpenID）
    private Long CreateTime;   // 消息创建时间 （整型）
    private String MsgType;   // text/image/event/voice...
    private Long MsgId;   // 消息id，64位整型
    private String Content;   // 文本消息内容
    private String PicUrl;	//图片链接（由系统生成）
    private String MediaId;	//图片消息媒体id，可以调用多媒体文件下载接口拉取数据。
    private String Event; //事件类型 subscribe(订阅)、unsubscribe(取消订阅)、CLICK(点击菜单)
    private String EventKey; //菜单的key值
    private String Latitude;  //地理位置纬度
    private String Longitude; //地理位置经度
    private String Precision; //地理位置精度
    private String Recognition; //语音识别结果
}
