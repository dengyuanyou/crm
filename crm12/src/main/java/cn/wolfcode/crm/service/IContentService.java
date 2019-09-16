package cn.wolfcode.crm.service;

import cn.wolfcode.crm.domain.Content;

public interface IContentService {
    Content selectByContentId(Long contentId);

    void UpdateContent(Content content);

}
