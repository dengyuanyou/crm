package cn.wolfcode.wx.vo;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Setter
@Getter
@XmlAccessorType(XmlAccessType.FIELD)
public class Voice {
    private String[] MediaId;	//语音消息媒体id，可以调用多媒体文件下载接口拉取数据。
    public Voice(String[] mediaId) {
        MediaId = mediaId;
    }
}
