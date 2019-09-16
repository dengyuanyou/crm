package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.Content;
import java.util.List;

public interface ContentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Content record);

    Content selectByPrimaryKey(Long id);

    List<Content> selectAll();

    int updateByPrimaryKey(Content record);

    Content selectByContentId(Long contentId);
}