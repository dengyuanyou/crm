package cn.wolfcode.crm.service;

import cn.wolfcode.crm.domain.SystemDictionary;
import cn.wolfcode.crm.query.QueryObject;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ISystemDictionaryService {
    void save(SystemDictionary systemDictionary);
    void update(SystemDictionary systemDictionary);
    void delete(Long id);
    SystemDictionary get(Long id);
    List<SystemDictionary> listAll();

    PageInfo<SystemDictionary> query(QueryObject qo);
}
