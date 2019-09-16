package cn.wolfcode.crm.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Employee extends BaseDomain implements Serializable{
    private String name;
    private String password;
    private String email;
    private Integer age;
    private boolean admin;
    private Department dept;
    private List<Role> roles = new ArrayList<>();
}