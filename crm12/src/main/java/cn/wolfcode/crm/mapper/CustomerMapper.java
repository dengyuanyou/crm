package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.Customer;
import cn.wolfcode.crm.query.CustomerQueryObject;
import cn.wolfcode.crm.query.QueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomerMapper {

    int insert(Customer record);

    Customer selectByPrimaryKey(Long id);

    List<Customer> selectAll();

    int updateByPrimaryKey(Customer record);

    List<Customer> selectForList(QueryObject qo);

    void updateStatusByPrimaryKey(Customer c);

    void updateStatus(@Param("cid")Long cid, @Param("status") Integer status);

    List<Customer> selectForAll(CustomerQueryObject qo);

    List<Customer> failList(CustomerQueryObject qo);

    void updateNew(Customer customer);

    void updateBynewCustomer(Customer newCustomer);

    String selectTel(Long tel);

    Customer selectCustomerInfo(Long tel);

    List<Customer> listPotentialAndPoolCustomer();

}