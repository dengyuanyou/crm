package cn.wolfcode.wx.vo;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@Setter
@Getter
@XmlRootElement(name="xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class OutMsgEntity {
    private String ToUserName;   //  用户的OpenID
    private String FromUserName;   // 测试号的微信号
    private Long CreateTime;   // 消息创建时间 （整型）
    private String MsgType;   // text/image/...
    private String Content;   // 文本消息内容
    private Voice Voice; //语音
    private Image Image; //图片
    //图文消息
    private Integer ArticleCount;//图文个数
    //图文列表明细
    @XmlElementWrapper(name="Articles")
    private ArticleItem[] item;
}
