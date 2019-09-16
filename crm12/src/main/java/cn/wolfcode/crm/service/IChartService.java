package cn.wolfcode.crm.service;

import cn.wolfcode.crm.domain.Customer;
import cn.wolfcode.crm.query.QueryObject;
import com.github.pagehelper.PageInfo;

import java.util.List;import java.util.Map;

public interface IChartService {
    List<Map<String,Object>> queryCustomerChart(QueryObject qo);
}
