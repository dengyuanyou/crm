package cn.wolfcode.crm.service.impl;

import cn.wolfcode.crm.domain.Content;
import cn.wolfcode.crm.domain.Contract;
import cn.wolfcode.crm.domain.Customer;
import cn.wolfcode.crm.mapper.ContentMapper;
import cn.wolfcode.crm.mapper.ContractMapper;
import cn.wolfcode.crm.mapper.CustomerMapper;
import cn.wolfcode.crm.query.ContractAndContent;
import cn.wolfcode.crm.query.ContractQueryObject;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IContractService;
import cn.wolfcode.crm.util.StringUtil;
import cn.wolfcode.crm.util.UserContext;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ContractServiceImpl implements IContractService {

    @Autowired
    private ContractMapper contractMapper;
    @Autowired
    private ContentMapper contentMapper;
    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public PageInfo<Contract> query(QueryObject qo) {
        //在这行代码下面的第一个SQL中会拼接分页的SQL片段
        PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());
        List<Contract> list = contractMapper.selectForList(qo);
        return new PageInfo<>(list);
    }

    /**
     * 合同修改
     * @param contractAndContent
     */
    @Override
    public void update(ContractAndContent contractAndContent) {
        //获取合同内容对象
        Content content = contractAndContent.getContent();

        //修改合同内容对象
        contentMapper.updateByPrimaryKey(content);

        //获取合同对象
        Contract contract = contractAndContent.getContract();

        //重新将合同内容对象的id设置到合同对象的contentId上
        contract.setContentId(content.getId());

        //修改合同对象
        contractMapper.updateByPrimaryKey(contract);
    }

    /**
     * 合同添加
     * @param contractAndContent
     */
    @Override
    public void save(ContractAndContent contractAndContent) {
        //获取合同内容对象
        Content content = contractAndContent.getContent();
        //将接受的字符串进行utf-8编码
        //String explain = StringUtil.getUTF8StringFromGBKString(content.getExplain());
        //添加合同内容对象
        contentMapper.insert(content);
        //获取合同对象
        Contract contract = contractAndContent.getContract();
        //将合同内容对象的id设置到合同对象的contentId上
        contract.setContentId(content.getId());
        //添加合同对象
        contractMapper.insert(contract);

        //将当前合同对象中客户的状态修改为开发成功
        Customer customer = customerMapper.selectByPrimaryKey(contract.getCustomer().getId());
        Customer newCustomer = new Customer();
        newCustomer.setId(customer.getId());
        newCustomer.setName(customer.getName());
        newCustomer.setAge(customer.getAge());
        newCustomer.setGender(customer.getGender());
        newCustomer.setTel(customer.getTel());
        newCustomer.setQq(customer.getQq());
        newCustomer.setJob(customer.getJob());
        newCustomer.setSource(customer.getSource());
        newCustomer.setSeller(customer.getSeller());
        newCustomer.setInputUser(UserContext.getCurrentEmp());
        newCustomer.setInputTime(new Date());
        newCustomer.setStatus(5);

        //修改客户状态
        customerMapper.updateByPrimaryKey(newCustomer);
    }

    /**
     * 删除合同
     * @param contractId
     */
    @Override
    public void deleteContract(Long contractId) {
        //根据当前合同对象获取合同内容的id
        Contract contract = contractMapper.selectByPrimaryKey(contractId);

        //根据合同内容id删除合同内容
        contentMapper.deleteByPrimaryKey(contract.getContentId());

        //删除合同对象
        contractMapper.deleteByPrimaryKey(contractId);

        //将这个合同的客户的状态修改为潜在客户
        customerMapper.updateStatus(contract.getCustomer().getId(),1);

    }

    /**
     * 根据当前登录用户id查询当前用户的合同列表
     * @param qo
     * @return
     */
    @Override
    public PageInfo<Contract> queryMyContract(ContractQueryObject qo) {
        //在这行代码下面的第一个SQL中会拼接分页的SQL片段
        PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());
        List<Contract> list = contractMapper.queryMyContract(qo);
        return new PageInfo<>(list);
    }

    /**
     * 根据合同id查询合同的信息
     * @param id
     * @return
     */
    @Override
    public Contract getContract(Long id) {
        return contractMapper.getContract(id);
    }

}
