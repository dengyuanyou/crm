package cn.wolfcode.wx.service;

import cn.wolfcode.wx.domain.Inform;

import java.util.List;

public interface IInformService {
    List<Inform> selectAllInform();

    void deleteAllInform();

}
