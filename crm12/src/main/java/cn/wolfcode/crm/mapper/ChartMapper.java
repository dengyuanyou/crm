package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.query.QueryObject;

import java.util.List;
import java.util.Map;

public interface ChartMapper {

    //查询潜在客户的报表数据
   List<Map<String,Object>> selectCustomerChart(QueryObject qo);
}