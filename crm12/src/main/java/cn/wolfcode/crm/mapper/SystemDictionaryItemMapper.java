package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.SystemDictionary;
import cn.wolfcode.crm.domain.SystemDictionaryItem;
import cn.wolfcode.crm.query.QueryObject;

import java.util.List;

public interface SystemDictionaryItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SystemDictionaryItem record);

    SystemDictionaryItem selectByPrimaryKey(Long id);

    List<SystemDictionaryItem> selectAll();

    int updateByPrimaryKey(SystemDictionaryItem record);

    List<SystemDictionaryItem> selectForList(QueryObject qo);

    void deleteByParentId(Long id);

    List<SystemDictionaryItem> selectItemByDicSn(String dicSn);
}