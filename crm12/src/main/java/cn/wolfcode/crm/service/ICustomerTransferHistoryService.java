package cn.wolfcode.crm.service;

import cn.wolfcode.crm.domain.CustomerTransferHistory;
import cn.wolfcode.crm.query.QueryObject;
import com.github.pagehelper.PageInfo;

public interface ICustomerTransferHistoryService {
    void save(CustomerTransferHistory customerTransferHistory);

    PageInfo<CustomerTransferHistory> query(QueryObject qo);
}
