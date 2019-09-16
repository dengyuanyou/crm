package cn.wolfcode.crm.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JsonResult {
    private boolean success = true;
    private String msg;

    //当错误的时候,使用该方法来封装数据
    public void mark(String msg){
        this.success=false;
        this.msg=msg;
    }
}
