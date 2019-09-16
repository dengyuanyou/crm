package cn.wolfcode.wx.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 菜单基类
 */
@Setter
@Getter
@ToString
public class Button {

    private String type;    //菜单类型
    private String name;    //菜单名称
    private Button[] sub_button;    //二级菜单

}
