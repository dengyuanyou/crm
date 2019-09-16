package cn.wolfcode.crm.util;

import cn.wolfcode.crm.query.ChartQueryObject;

public abstract class MessageUtil {
    public static String changMsg(ChartQueryObject qo) {
        String msg = null;
        switch (qo.getGroupType()) {
            case "DATE_FORMAT(c.input_time, '%Y')":
                msg = "年份";
                break;
            case "DATE_FORMAT(c.input_time, '%Y-%m')":
                msg = "月份";
                break;
            case "DATE_FORMAT(c.input_time, '%Y-%m-%d')":
                msg = "日期";
                break;
            default:
                msg = "销售员";
        }
        return msg;
    }
}
