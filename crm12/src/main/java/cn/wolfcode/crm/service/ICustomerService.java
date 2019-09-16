package cn.wolfcode.crm.service;

import cn.wolfcode.crm.domain.Customer;
import cn.wolfcode.crm.domain.CustomerTransferHistory;
import cn.wolfcode.crm.query.CustomerQueryObject;
import cn.wolfcode.crm.query.QueryObject;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ICustomerService {
    void save(Customer customer);

    void update(Customer customer);

    Customer get(Long id);

    List<Customer> listAll();

    PageInfo<Customer> query(QueryObject qo);

    void updateStatus(Long cid, Integer status);

    PageInfo<Customer> queryAll(CustomerQueryObject qo);

    PageInfo<Customer> failList(CustomerQueryObject qo);

    void failTransfer(Customer customer);

    void distribute(CustomerTransferHistory customerTransferHistory);

    Customer selectCustomerInfo(Long tel);

    List<Customer> listPotentialAndPoolCustomer();
}
