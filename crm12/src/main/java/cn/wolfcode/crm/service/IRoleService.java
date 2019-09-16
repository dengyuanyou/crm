package cn.wolfcode.crm.service;

import cn.wolfcode.crm.domain.Role;
import cn.wolfcode.crm.query.QueryObject;
import com.github.pagehelper.PageInfo;

import java.util.List;import java.util.Set;

public interface IRoleService {
    void save(Role role, Long[] ids);

    void update(Role role, Long[] ids);

    void delete(Long id);

    Role get(Long id);

    List<Role> listAll();

    // 高级查询分页查询
    PageInfo<Role> query(QueryObject qo);

    List<String> listRoleSnByEmpId(Long employeeId);
}