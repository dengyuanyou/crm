package cn.wolfcode.crm.quartz;

import cn.wolfcode.wx.service.IInformService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 使用定时器每天的23:59:59定时执行清除inform表中的数据
 */
public class QuartzJob {

    @Autowired
    private IInformService iInformService;

    public void work() {

        try {
            //注入inform的service方法；
            iInformService.deleteAllInform();
            //执行删除消息记录的方法
        }catch (Exception e){
            e.getMessage();
        }
    }
}
