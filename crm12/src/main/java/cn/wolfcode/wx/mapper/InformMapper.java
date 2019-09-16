package cn.wolfcode.wx.mapper;

import cn.wolfcode.wx.domain.Inform;
import java.util.List;

public interface InformMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Inform record);

    Inform selectByPrimaryKey(Long id);

    List<Inform> selectAll();

    int updateByPrimaryKey(Inform record);

    void deleteAll();

    void deleteAllInform();

}