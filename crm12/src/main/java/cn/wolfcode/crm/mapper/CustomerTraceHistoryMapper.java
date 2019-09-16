package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.CustomerTraceHistory;
import cn.wolfcode.crm.domain.Department;
import cn.wolfcode.crm.query.QueryObject;

import java.util.List;

public interface CustomerTraceHistoryMapper {

    int insert(CustomerTraceHistory record);

    int updateByPrimaryKey(CustomerTraceHistory record);

    List<CustomerTraceHistory> selectForList(QueryObject qo);
}