package cn.wolfcode.crm.service;


import cn.wolfcode.crm.domain.Contract;
import cn.wolfcode.crm.query.ContractAndContent;
import cn.wolfcode.crm.query.ContractQueryObject;
import cn.wolfcode.crm.query.QueryObject;
import com.github.pagehelper.PageInfo;

public interface IContractService {

    PageInfo<Contract> query(QueryObject qo);

    void update(ContractAndContent contractAndContent);

    void save(ContractAndContent contractAndContent);

    void deleteContract(Long contractId);

    PageInfo<Contract> queryMyContract(ContractQueryObject qo);

    Contract getContract(Long id);
}
