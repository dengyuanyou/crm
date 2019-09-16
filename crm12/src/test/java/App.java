import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class App {


    @Test
    public void testFreeMark() throws Exception {
        Configuration conf = new Configuration();
        conf.setDirectoryForTemplateLoading(new File("e:/"));
        conf.setObjectWrapper(new DefaultObjectWrapper());
        Template template = conf.getTemplate("department.ftl");
        System.out.println(template);

        //准备数据
        Map<String,Object> map = new HashMap<>();
        map.put("name","XXXX");

        Writer out = new OutputStreamWriter(new FileOutputStream(new File("e:/department.html")));
        template.process(map,out);
        out.flush();
        out.close();
    }


    @Test
    public void testLogin() {
        //创建安全管理器
        IniSecurityManagerFactory factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        SecurityManager manager = factory.getInstance();
        //将安全管理器设置到运行环境中
        SecurityUtils.setSecurityManager(manager);
        //获取用户的主体对象
        Subject subject = SecurityUtils.getSubject();
        System.out.println("是否通过认证:"+subject.isAuthenticated());
        try {
            subject.login(new UsernamePasswordToken("admin","123"));
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        subject.logout();
        System.out.println("是否通过认证:"+subject.isAuthenticated());
    }
    @Test
    public void testLogin2() {
        //创建安全管理器
        IniSecurityManagerFactory factory = new IniSecurityManagerFactory("classpath:shiro-realm.ini");
        SecurityManager manager = factory.getInstance();
        //将安全管理器设置到运行环境中
        SecurityUtils.setSecurityManager(manager);
        //获取用户的主体对象
        Subject subject = SecurityUtils.getSubject();
        System.out.println("是否通过认证:"+subject.isAuthenticated());
        try {
            subject.login(new UsernamePasswordToken("admin","1"));
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
//        subject.logout();
        System.out.println("是否通过认证:"+subject.isAuthenticated());
    }

    @Test
    public void testMd5() {
        Md5Hash hash = new Md5Hash("1","SB");
        System.out.println(hash);
    }



    @Test
    public void testLogin3() {
        //创建安全管理器
        IniSecurityManagerFactory factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        SecurityManager manager = factory.getInstance();
        //将安全管理器设置到运行环境中
        SecurityUtils.setSecurityManager(manager);
        //获取用户的主体对象
        Subject subject = SecurityUtils.getSubject();
        System.out.println("是否通过认证:"+subject.isAuthenticated());
        try {
            subject.login(new UsernamePasswordToken("admin","123"));
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        System.out.println("是否通过认证:"+subject.isAuthenticated());


        System.out.println(subject.hasAllRoles(Arrays.asList("role1")));
        System.out.println(subject.hasRole("role2"));
        System.out.println(Arrays.toString(subject.hasRoles(Arrays.asList("role1","role2"))));

        System.out.println(subject.isPermitted("department:delete"));
        System.out.println(subject.isPermitted("department:update"));
    }


    @Test
    public void testLogin4() {
        //创建安全管理器
        IniSecurityManagerFactory factory = new IniSecurityManagerFactory("classpath:shiro-realm.ini");
        SecurityManager manager = factory.getInstance();
        //将安全管理器设置到运行环境中
        SecurityUtils.setSecurityManager(manager);
        //获取用户的主体对象
        Subject subject = SecurityUtils.getSubject();
        System.out.println("是否通过认证:"+subject.isAuthenticated());
        try {
            subject.login(new UsernamePasswordToken("admin","123"));
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        System.out.println("是否通过认证:"+subject.isAuthenticated());


        System.out.println(subject.hasAllRoles(Arrays.asList("role1")));//true
        System.out.println(subject.hasRole("role2"));//false
        System.out.println(Arrays.toString(subject.hasRoles(Arrays.asList("role1","role2"))));//true  false

        System.out.println(subject.isPermitted("department:delete"));//true
        System.out.println(subject.isPermitted("department:update"));//false
    }

    @Test
    public void testPOI() throws Exception {
        //创建工作簿
        Workbook book = new HSSFWorkbook();
        //创建sheet
        Sheet sheet = book.createSheet("员工信息");
        //创建行
        Row row = sheet.createRow(0);
        //创建单元格
        Cell cell1 = row.createCell(0);
        Cell cell2 = row.createCell(1);
        Cell cell3 = row.createCell(2);
        //设置数据
        cell1.setCellValue("账号");
        cell2.setCellValue("邮箱");
        cell3.setCellValue("年龄");
        //写到磁盘中
        book.write(new FileOutputStream(new File("e:/employee.xls")));
    }
}
