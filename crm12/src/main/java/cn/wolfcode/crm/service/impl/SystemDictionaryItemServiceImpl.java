package cn.wolfcode.crm.service.impl;

import cn.wolfcode.crm.domain.SystemDictionaryItem;
import cn.wolfcode.crm.mapper.SystemDictionaryItemMapper;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.ISystemDictionaryItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemDictionaryItemServiceImpl implements ISystemDictionaryItemService {


    @Autowired
    private SystemDictionaryItemMapper systemDictionaryItemMapper;

    @Override
    public void save(SystemDictionaryItem systemDictionaryItem) {
        systemDictionaryItemMapper.insert(systemDictionaryItem);
    }

    @Override
    public void update(SystemDictionaryItem systemDictionaryItem) {
        systemDictionaryItemMapper.updateByPrimaryKey(systemDictionaryItem);
    }

    @Override
    public void delete(Long id) {
        systemDictionaryItemMapper.deleteByPrimaryKey(id);
    }

    @Override
    public SystemDictionaryItem get(Long id) {
        return systemDictionaryItemMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SystemDictionaryItem> listAll() {
        return systemDictionaryItemMapper.selectAll();
    }

    @Override
    public PageInfo<SystemDictionaryItem> query(QueryObject qo) {
        //在这行代码下面的第一个SQL中会拼接分页的SQL片段
        PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize(), qo.getOrderBy());
        List<SystemDictionaryItem> list = systemDictionaryItemMapper.selectForList(qo);
        return new PageInfo<>(list);

    }

    @Override
    public List<SystemDictionaryItem> listJobs() {
        return systemDictionaryItemMapper.selectItemByDicSn("job");

    }

    @Override
    public List<SystemDictionaryItem> listSources() {
        return systemDictionaryItemMapper.selectItemByDicSn("source");
    }

    @Override
    public List<SystemDictionaryItem> listTypes() {
        return systemDictionaryItemMapper.selectItemByDicSn("communicationMethod");
    }
}
