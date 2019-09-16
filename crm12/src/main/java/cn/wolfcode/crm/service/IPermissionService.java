package cn.wolfcode.crm.service;

import cn.wolfcode.crm.domain.Permission;
import cn.wolfcode.crm.query.QueryObject;import com.github.pagehelper.PageInfo;

import java.util.List;import java.util.Set;

public interface IPermissionService {
    void delete(Long id);

    List<Permission> listAll();

    PageInfo<Permission> query(QueryObject qo);

    /**
     * 加载权限
     */
    void load();

    Set<String> listExpressionsByEmpId(Long empId);
}
