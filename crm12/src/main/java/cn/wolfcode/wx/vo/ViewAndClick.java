package cn.wolfcode.wx.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 视图和点击菜单
 */
@Setter
@Getter
@ToString
public class ViewAndClick {

    private ViewButton viewButton;    //视图菜单
    private ClickButton clickButton;    //点击菜单
    private Button button;  //二级菜单


}
