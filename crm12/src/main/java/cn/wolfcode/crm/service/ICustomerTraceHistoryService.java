package cn.wolfcode.crm.service;

import cn.wolfcode.crm.domain.CustomerTraceHistory;
import cn.wolfcode.crm.query.QueryObject;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ICustomerTraceHistoryService {
    void save(CustomerTraceHistory customerTraceHistory);

    void update(CustomerTraceHistory customerTraceHistory);

    PageInfo<CustomerTraceHistory> query(QueryObject qo);
}
