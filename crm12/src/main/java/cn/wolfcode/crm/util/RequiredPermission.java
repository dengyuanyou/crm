package cn.wolfcode.crm.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)//当前注解只能贴在方法上
@Retention(RetentionPolicy.RUNTIME)//该注解能够保存到运行时期
public @interface RequiredPermission {
    String[] value();//接收传递进来的权限名称

}
