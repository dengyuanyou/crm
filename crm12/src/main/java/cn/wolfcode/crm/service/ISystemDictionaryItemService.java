package cn.wolfcode.crm.service;

import cn.wolfcode.crm.domain.SystemDictionaryItem;
import cn.wolfcode.crm.query.QueryObject;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ISystemDictionaryItemService {
    void save(SystemDictionaryItem systemDictionaryItem);

    void update(SystemDictionaryItem systemDictionaryItem);

    void delete(Long id);

    SystemDictionaryItem get(Long id);

    List<SystemDictionaryItem> listAll();

    PageInfo<SystemDictionaryItem> query(QueryObject qo);

    List<SystemDictionaryItem> listJobs();

    List<SystemDictionaryItem> listSources();

    List<SystemDictionaryItem> listTypes();
}
