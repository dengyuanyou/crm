package cn.wolfcode.crm.service.impl;

import cn.wolfcode.crm.domain.Role;
import cn.wolfcode.crm.mapper.RoleMapper;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;import java.util.Set;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public void save(Role role, Long[] permissionIds) {
        roleMapper.insert(role);

        if(permissionIds!=null){
            for (Long permissionId : permissionIds) {
                roleMapper.insertRolePermissionRelation(role.getId(),permissionId);
            }
        }
    }

    @Override
    public void update(Role role, Long[] permissionIds) {
        roleMapper.updateByPrimaryKey(role);

        roleMapper.deleteRolePermissionRelation(role.getId());

        if(permissionIds!=null){
            for (Long permissionId : permissionIds) {
                roleMapper.insertRolePermissionRelation(role.getId(),permissionId);
            }
        }


    }

    @Override
    public void delete(Long id) {
        roleMapper.deleteByPrimaryKey(id);
        roleMapper.deleteRolePermissionRelation(id);
    }

    @Override
    public Role get(Long id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Role> listAll() {
        return roleMapper.selectAll();
    }

    @Override
    public PageInfo<Role> query(QueryObject qo) {
        PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());
        List<Role> list = roleMapper.selectForList(qo);
        return new PageInfo(list);
    }

    @Override
    public List<String> listRoleSnByEmpId(Long employeeId) {
        return roleMapper.selectRoleSnByEmpId(employeeId);
    }
}
