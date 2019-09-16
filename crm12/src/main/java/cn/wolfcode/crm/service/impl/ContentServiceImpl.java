package cn.wolfcode.crm.service.impl;

import cn.wolfcode.crm.domain.Content;
import cn.wolfcode.crm.mapper.ContentMapper;
import cn.wolfcode.crm.service.IContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 合同内容相关
 */
@Service
public class ContentServiceImpl implements IContentService {

    @Autowired
    private ContentMapper contentMapper;

    /**
     * 根据合同内容id查询合同内容明细
     * @param contentId
     * @return
     */
    @Override
    public Content selectByContentId(Long contentId) {
        return contentMapper.selectByContentId(contentId);
    }

    /**
     * 修改合同内容
     * @param content
     */
    @Override
    public void UpdateContent(Content content) {
        contentMapper.updateByPrimaryKey(content);
    }

}
