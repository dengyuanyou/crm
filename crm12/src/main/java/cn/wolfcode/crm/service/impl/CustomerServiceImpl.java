package cn.wolfcode.crm.service.impl;

import cn.wolfcode.crm.domain.Customer;
import cn.wolfcode.crm.domain.CustomerTransferHistory;
import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.mapper.CustomerMapper;
import cn.wolfcode.crm.mapper.EmployeeMapper;
import cn.wolfcode.crm.query.CustomerQueryObject;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.ICustomerService;
import cn.wolfcode.crm.util.UserContext;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CustomerServiceImpl implements ICustomerService {


    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public void save(Customer customer) {
        //设置录入人和录入时间
        Employee emp = UserContext.getCurrentEmp();
        customer.setInputUser(emp);
        customer.setInputTime(new Date());
        customerMapper.insert(customer);
    }

    @Override
    public void update(Customer customer) {
        customerMapper.updateByPrimaryKey(customer);
    }

    @Override
    public Customer get(Long id) {
        return customerMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Customer> listAll() {
        return customerMapper.selectAll();
    }

    @Override
    public PageInfo<Customer> query(QueryObject qo) {
        //在这行代码下面的第一个SQL中会拼接分页的SQL片段
        PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());
        List<Customer> list = customerMapper.selectForList(qo);
        return new PageInfo<>(list);

    }

    @Override
    public void updateStatus(Long cid, Integer status) {
        customerMapper.updateStatus(cid,status);
    }

    /**
     * 查询客户列表
     * @param qo
     * @return
     */
    @Override
    public PageInfo<Customer> queryAll(CustomerQueryObject qo) {
        PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());
        List<Customer> list = customerMapper.selectForAll(qo);
        return new PageInfo<>(list);
    }

    /**
     * 查询失败客户列表
     * @param qo
     * @return
     */
    @Override
    public PageInfo<Customer> failList(CustomerQueryObject qo) {
        PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());
        List<Customer> list = customerMapper.failList(qo);
        return new PageInfo<>(list);
    }

    /**
     * 客户转让
     * @param customer
     */
    @Override
    public void failTransfer(Customer customer) {
        //获取当前的用户
        Employee newEmployee = UserContext.getCurrentEmp();

        //如果当前的员工不是管理员
        if (!newEmployee.getName().equals("admin")) {
            //旧的销售人员
            Long oldSellId = customer.getSeller().getId();

            //如果当前的用户是开发这个客户的用户则抛出异常
            if(newEmployee.getId()!=oldSellId) {
                Customer c = new Customer();
                c.setId(customer.getId());
                c.setName(customer.getName());
                c.setAge(customer.getAge());
                c.setGender(customer.getGender());
                c.setTel(customer.getTel());
                c.setQq(customer.getQq());
                c.setJob(customer.getJob());
                c.setSource(customer.getSource());
                //将当前用户的id设置到客户的sellr_id上
                c.setSeller(newEmployee);
                c.setInputUser(customer.getInputUser());
                c.setInputTime(new Date());
                //修改当前的状态
                c.setStatus(1);
                //保存这个客户对象
                customerMapper.updateNew(c);
        }else {
                throw new RuntimeException("对不起，您开发失败过这个客户，不能继续跟进！");
            }

        }else {
            throw new RuntimeException("对不起，您是管理员，不能跟进客户！");
        }
    }

    /**
     * 失败客户分配
     * @param customerTransferHistory
     */
    @Override
    public void distribute(CustomerTransferHistory customerTransferHistory) {
        //获取新的销售人员的id
        Employee newSeller = customerTransferHistory.getNewSeller();

        //获取旧的销售人员的id
        Long oldSellerId = customerTransferHistory.getOldSeller().getId();

        if(newSeller.getId()!=oldSellerId) {
            //将新的销售人员的id设置到当前失败客户的销售人员上
            Customer oldCustomer = customerMapper.selectByPrimaryKey(customerTransferHistory.getCustomer().getId());

            Customer newCustomer = new Customer();
            newCustomer.setId(oldCustomer.getId());
            newCustomer.setName(oldCustomer.getName());
            newCustomer.setAge(oldCustomer.getAge());
            newCustomer.setGender(oldCustomer.getGender());
            newCustomer.setTel(oldCustomer.getTel());
            newCustomer.setQq(oldCustomer.getQq());
            newCustomer.setJob(oldCustomer.getJob());
            newCustomer.setSource(oldCustomer.getSource());

            newCustomer.setSeller(newSeller);

            newCustomer.setInputUser(UserContext.getCurrentEmp());

            //设置新的时间
            newCustomer.setInputTime(new Date());

            //修改这个客户的状态值为潜在客户值1
            newCustomer.setStatus(1);

            //修改这个客户对象
            customerMapper.updateBynewCustomer(newCustomer);
        }else {
            throw new RuntimeException("开发失败的销售人员不能再次跟进当前客户！");
        }


    }

    /**
     * 根据客户id查询当前这个客户信息
     * @param tel
     * @return
     */
    @Override
    public Customer selectCustomerInfo(Long tel) {
        return customerMapper.selectCustomerInfo(tel);
    }

    /**
     * 查询状态为潜在客户和客户池的所有客户信息
     * @return
     */
    @Override
    public List<Customer> listPotentialAndPoolCustomer() {
        return customerMapper.listPotentialAndPoolCustomer();
    }
}
