package cn.wolfcode.crm.shiro.realm;

import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.service.IEmployeeService;
import cn.wolfcode.crm.service.IPermissionService;
import cn.wolfcode.crm.service.IRoleService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.security.provider.MD5;

import java.util.ArrayList;
import java.util.List;import java.util.Set;

@Component("realm")
public class MyRealm extends AuthorizingRealm {

    //使用容器中配置的凭证匹配器
    @Autowired
    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        super.setCredentialsMatcher(credentialsMatcher);
    }

    @Autowired
    private IEmployeeService service;

    //获取认证信息
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取用户传递过来的账号
        String principal = (String) token.getPrincipal();
        //根据用户的账号去数据库中查询用户信息
        Employee emp = service.getEmpByUsername(principal);
        System.out.println(token.getCredentials());
        //校验
        if (emp == null) {
            return null;
        }

        //用户名为盐，将用户的密码进行MD5加盐
        return new SimpleAuthenticationInfo(emp, emp.getPassword(), ByteSource.Util.bytes(emp.getName()), getName());

    }

    @Autowired
    private IRoleService roleService;
    @Autowired
    private IPermissionService permissionService;
    //获取授权信息
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //创建一个授权的对象
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        //获取到用户的身份
        Employee employee= (Employee) principalCollection.getPrimaryPrincipal();

        //授权
        if(employee.isAdmin()){
            info.addRole("admin");
            info.addStringPermission("*:*");
        }else{
            //查询当前用户拥有的所有的角色
            List<String> roles = roleService.listRoleSnByEmpId(employee.getId());
            //查询当前用户拥有的所有的权限表达式
            Set<String> permissions = permissionService.listExpressionsByEmpId(employee.getId());

            info.addRoles(roles);
            info.addStringPermissions(permissions);
        }

        return info;
    }


}
