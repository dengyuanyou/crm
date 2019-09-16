package cn.wolfcode.wx.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * 视图型菜单
 */
@Setter
@Getter
@ToString
public class ViewButton extends Button{

    private String url;   //View类型菜单url


}
