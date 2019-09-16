package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.Contract;
import cn.wolfcode.crm.query.ContractQueryObject;
import cn.wolfcode.crm.query.QueryObject;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ContractMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Contract record);

    Contract selectByPrimaryKey(Long id);

    List<Contract> selectAll();

    int updateByPrimaryKey(Contract record);

    List<Contract> selectForList(QueryObject qo);

    List<Contract> queryMyContract(ContractQueryObject qo);

    Contract getContract(Long id);
}