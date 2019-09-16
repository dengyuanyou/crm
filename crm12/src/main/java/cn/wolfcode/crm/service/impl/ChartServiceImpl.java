package cn.wolfcode.crm.service.impl;

import cn.wolfcode.crm.mapper.ChartMapper;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ChartServiceImpl implements IChartService {

    @Autowired
    private ChartMapper chartMapper;

    @Override
    public List<Map<String, Object>> queryCustomerChart(QueryObject qo) {
        return chartMapper.selectCustomerChart(qo);
    }
}
