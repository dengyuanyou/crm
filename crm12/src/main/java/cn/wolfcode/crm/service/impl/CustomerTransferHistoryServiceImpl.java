package cn.wolfcode.crm.service.impl;

import cn.wolfcode.crm.domain.Customer;
import cn.wolfcode.crm.domain.CustomerTransferHistory;
import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.mapper.CustomerMapper;
import cn.wolfcode.crm.mapper.CustomerTransferHistoryMapper;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.ICustomerTransferHistoryService;
import cn.wolfcode.crm.util.UserContext;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CustomerTransferHistoryServiceImpl implements ICustomerTransferHistoryService {

    @Autowired
    private CustomerTransferHistoryMapper customerTransferHistoryMapper;
    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public void save(CustomerTransferHistory customerTransferHistory) {
        //设置录入人和录入时间
        Employee emp = UserContext.getCurrentEmp();
        customerTransferHistory.setOperator(emp);
        customerTransferHistory.setOperateTime(new Date());
        customerTransferHistoryMapper.insert(customerTransferHistory);

        //在移交之后,需要将当前客户的状态修改为潜在客户
        //营销人员修改为newSeller
        Long id = customerTransferHistory.getCustomer().getId();

        //获取到修改之前的客户信息
        Customer c = new Customer();

        c.setStatus(Customer.STATUS_POTENTIAL);
        c.setSeller(customerTransferHistory.getNewSeller());
        c.setId(id);
        customerMapper.updateStatusByPrimaryKey(c);
    }

    @Override
    public PageInfo<CustomerTransferHistory> query(QueryObject qo) {
        //在这行代码下面的第一个SQL中会拼接分页的SQL片段
        PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize(),qo.getOrderBy());
        List<CustomerTransferHistory> list = customerTransferHistoryMapper.selectForList(qo);
        return new PageInfo<>(list);

    }
}
