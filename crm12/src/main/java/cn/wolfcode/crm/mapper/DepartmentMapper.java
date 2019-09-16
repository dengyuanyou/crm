package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.Department;
import cn.wolfcode.crm.query.QueryObject;

import java.util.List;

public interface DepartmentMapper {
    void insert(Department department);

    void updateByPrimaryKey(Department department);

    void deleteByPrimaryKey(Long id);

    Department selectByPrimaryKey(Long id);

    List<Department> selectAll();

    List<Department> selectForList(QueryObject qo);
}
