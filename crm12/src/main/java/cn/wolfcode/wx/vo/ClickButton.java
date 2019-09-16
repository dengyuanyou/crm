package cn.wolfcode.wx.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 点击型菜单
 */
@Setter
@Getter
@ToString
public class ClickButton extends Button{

    private String key;     //Click类型菜单key

}
