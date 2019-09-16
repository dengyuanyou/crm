package cn.wolfcode.crm.service.impl;

import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.domain.Permission;
import cn.wolfcode.crm.mapper.PermissionMapper;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IPermissionService;
import cn.wolfcode.crm.util.RequiredPermission;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.*;

@Service
public class PermissionServiceImpl implements IPermissionService {

    @Autowired
    private PermissionMapper permissionMapper;


    @Override
    public void delete(Long id) {
        permissionMapper.deleteByPrimaryKey(id);
    }


    @Override
    public List<Permission> listAll() {
        return permissionMapper.selectAll();
    }

    @Override
    public PageInfo<Permission> query(QueryObject qo) {
        PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());
        List<Permission> list = permissionMapper.selectForList(qo);
        return new PageInfo(list);
    }

    // 1.获取到Spring容器对象
    @Autowired
    private ApplicationContext ctx;

    @Override
    public void load() {
        //查询所有的权限表达式
        List<String> expressions = permissionMapper.selectAllExpressions();

        //<bean id=""  class="">
        // 2.从容器中获取到所有的Controller对象
        Map<String, Object> beansWithAnnotation = ctx.getBeansWithAnnotation(Controller.class);
        Collection<Object> controllers = beansWithAnnotation.values();
        //
        // 3.获取Controller对象中的所有的方法
        for (Object controller : controllers) {
            System.out.println(controller.getClass());
            if (AopUtils.isCglibProxy(controller)) {
                //获取父类对象中的方法
                Method[] methods = controller.getClass().getSuperclass().getMethods();
                for (Method method : methods) {
                    // 4.判断每个方法是否有贴@RequiredPermission注解
                    if (method.isAnnotationPresent(RequiresPermissions.class)) {
                        // 权限名称:当前方法上注解@RequiredPermission的value值
                        RequiresPermissions annotation = method.getAnnotation(RequiresPermissions.class);
                        String[] values = annotation.value();
                        String expression = values[1];
                        if (expressions.contains(expression)) {
                            continue;
                        }
                        // 5.将权限信息保存到数据库中
                        Permission p = new Permission();
                        p.setName(values[0]);
                        p.setExpression(expression);
                        permissionMapper.insert(p);
                    }
                }
            }
        }
    }
    @Override
    public Set<String> listExpressionsByEmpId(Long empId) {
        return permissionMapper.selectExpressionsByEmpId(empId);
    }
}
