package cn.wolfcode.wx.service.impl;

import cn.wolfcode.wx.domain.Inform;
import cn.wolfcode.wx.mapper.InformMapper;
import cn.wolfcode.wx.service.IInformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息通知相关
 */
@Service
public class InformServiceImpl implements IInformService {
    @Autowired
    private InformMapper informMapper;

    //定义一个存储查询消息通知id的集合
    private List<Object> inform = new ArrayList<>();

    /**
     * 查询所有的消息通知列表
     * @return
     */
    @Override
    public List<Inform> selectAllInform() {

        //遍历查询的集合列表
        for (Inform i:informMapper.selectAll()){
            //如果包含已经查询过的消息id返回null
            if(inform.contains(i.getId())){
                return null;
            }
            //否则将当前的消息通知id放入集合中
            inform.add(i.getId());
        }
        return informMapper.selectAll();

    }

    /**
     * 定时执行删除所有消息记录的SQL
     */
    @Override
    public void deleteAllInform() {
        informMapper.deleteAllInform();
    }

}
