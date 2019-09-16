package cn.wolfcode.crm.service.impl;

import cn.wolfcode.crm.domain.CustomerTraceHistory;
import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.mapper.CustomerTraceHistoryMapper;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.ICustomerTraceHistoryService;
import cn.wolfcode.crm.util.UserContext;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CustomerTraceHistoryServiceImpl implements ICustomerTraceHistoryService {


    @Autowired
    private CustomerTraceHistoryMapper customerTraceHistoryMapper;

    @Override
    public void save(CustomerTraceHistory customerTraceHistory) {
        //设置录入人和录入时间
        Employee emp = UserContext.getCurrentEmp();
        customerTraceHistory.setInputUser(emp);
        customerTraceHistory.setInputTime(new Date());
        customerTraceHistoryMapper.insert(customerTraceHistory);
    }

    @Override
    public void update(CustomerTraceHistory customerTraceHistory) {
        customerTraceHistoryMapper.updateByPrimaryKey(customerTraceHistory);
    }


    @Override
    public PageInfo<CustomerTraceHistory> query(QueryObject qo) {
        //在这行代码下面的第一个SQL中会拼接分页的SQL片段
        PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize(),qo.getOrderBy());
        List<CustomerTraceHistory> list = customerTraceHistoryMapper.selectForList(qo);
        return new PageInfo<>(list);

    }
}
