package cn.wolfcode.crm.domain;

import com.alibaba.druid.support.json.JSONUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Customer extends BaseDomain {
    public static final Integer STATUS_POTENTIAL = 1;
    public static final Integer STATUS_POOLED = 2;
    public static final Integer STATUS_FLOW = 3;
    public static final Integer STATUS_FAILURE = 4;
    public static final Integer STATUS_SUCCESS = 5;

    private String name;

    private Integer age;

    //1:男  0:女
    private Integer gender;

    public String getGenderName(){
        String genderName = null;
        if(gender==0){
            genderName="女";
        }else{
            genderName="男";
        }
        return genderName;
    }

    private String tel;

    private String qq;

    private SystemDictionaryItem job;//职业

    private SystemDictionaryItem source;//来源

    private Employee seller;//营销人员

    private Employee inputUser;

    private Date inputTime;

    //状态: 1,潜在客户  2,客户池  3,流失  4,开发失败 5.开发成功
    private Integer status = STATUS_POTENTIAL;

    public String getJsonString(){
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        map.put("name",name);
        map.put("age",age);
        map.put("gender",gender);
        map.put("tel",tel);
        map.put("qq",qq);
        map.put("jobId",job.id);
        map.put("sourceId",source.id);
        map.put("sellerId",seller.id);
        map.put("inputUserId",inputUser.id);
        map.put("sellerName",seller.getName());
        return JSONUtils.toJSONString(map);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public SystemDictionaryItem getJob() {
        return job;
    }

    public void setJob(SystemDictionaryItem job) {
        this.job = job;
    }

    public SystemDictionaryItem getSource() {
        return source;
    }

    public void setSource(SystemDictionaryItem source) {
        this.source = source;
    }

    public Employee getSeller() {
        return seller;
    }

    public void setSeller(Employee seller) {
        this.seller = seller;
    }

    public Employee getInputUser() {
        return inputUser;
    }

    public void setInputUser(Employee inputUser) {
        this.inputUser = inputUser;
    }

    public Date getInputTime() {
        return inputTime;
    }

    public void setInputTime(Date inputTime) {
        this.inputTime = inputTime;
    }

    public static Integer getStatusPotential() {
        return STATUS_POTENTIAL;
    }

    public static Integer getStatusPooled() {
        return STATUS_POOLED;
    }

    public static Integer getStatusFlow() {
        return STATUS_FLOW;
    }

    public static Integer getStatusFailure() {
        return STATUS_FAILURE;
    }

    public Integer getStatus() {

        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public static Integer getStatusSuccess() {
        return STATUS_SUCCESS;
    }
}