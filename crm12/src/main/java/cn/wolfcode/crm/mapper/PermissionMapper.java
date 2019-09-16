package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.Permission;
import cn.wolfcode.crm.query.QueryObject;

import java.util.List;
import java.util.Set;

public interface PermissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Permission record);

    List<Permission> selectAll();

    List<Permission> selectForList(QueryObject qo);

    List<String> selectAllExpressions();

    Set<String> selectExpressionsByEmpId(Long employeeId);
}