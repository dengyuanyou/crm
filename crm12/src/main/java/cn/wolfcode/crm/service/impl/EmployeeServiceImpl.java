package cn.wolfcode.crm.service.impl;


import cn.wolfcode.crm.domain.Customer;
import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.mapper.EmployeeMapper;
import cn.wolfcode.crm.mapper.PermissionMapper;
import cn.wolfcode.crm.query.ChartQueryObject;
import cn.wolfcode.crm.query.EmployeeQueryObject;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IEmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public void save(Employee employee, Long[] roleIds) {
        employee.setPassword(new Md5Hash(employee.getPassword(),employee.getName()).toString());
        employeeMapper.insert(employee);
        //保存员工和角色的关系
        if (roleIds != null) {
            for (Long roleId : roleIds) {
                employeeMapper.insertEmpRoleRelation(employee.getId(), roleId);
            }
        }
    }

    @Override
    public void update(Employee employee, Long[] roleIds) {
        employeeMapper.updateByPrimaryKey(employee);
        employeeMapper.deleteEmpRoleRelation(employee.getId());
        //保存员工和角色的关系
        if (roleIds != null) {
            for (Long roleId : roleIds) {
                employeeMapper.insertEmpRoleRelation(employee.getId(), roleId);
            }
        }
    }

    @Override
    public void delete(Long id) {
        employeeMapper.deleteByPrimaryKey(id);
        employeeMapper.deleteEmpRoleRelation(id);

    }

    @Override
    public Employee get(Long id) {
        Employee employee = employeeMapper.selectByPrimaryKey(id);
        System.out.println(employee.getClass());
        return employee;
    }

    @Override
    public List<Employee> listAll() {
        return employeeMapper.selectAll();
    }

    @Override
    public PageInfo<Employee> query(EmployeeQueryObject qo) {
        PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());
        List<Employee> list = employeeMapper.selectForList(qo);
        return new PageInfo(list);
    }

    @Override
    public void login(String name, String password) {
       /* Employee employee = employeeMapper.queryByNameAndPassword(name, password);
        if (employee == null) {
            throw new LogicException("亲,您的账号或者密码错误");
        }
        //将当前用户共享到session中
        UserContext.setCurrentEmp(employee);

        //查询到当前用户拥有的权限表达式,再共享到session中
        //如果用户拥有的多个角色中,存在相同的权限,此时会查询到多个相同的权限表达式
        //去重:1,在sql中使用distinct实现   2,在java中Set集合来接收
        Set<String> expressions = permissionMapper.selectExpressionsByEmpId(employee.getId());;
        UserContext.setExpressions(expressions);*/
    }

    @Override
    public void batchDelete(Long[] ids) {
        employeeMapper.deleteByBatch(ids);
    }

    @Override
    public Employee getEmpByUsername(String username) {
        return employeeMapper.selectByUsername(username);
    }

    @Override
    public Workbook exportExcel(EmployeeQueryObject qo) {
        //查询到需要导出的数据
        List<Employee> emps = employeeMapper.selectEmpForExport(qo);
        //创建workbook,并返回
        Workbook book = new HSSFWorkbook();
        //创建sheet
        Sheet sheet = book.createSheet("员工信息");
        //创建行(表头行)
        Row row = sheet.createRow(0);
        //创建单元格
        Cell cell1 = row.createCell(0);
        Cell cell2 = row.createCell(1);
        Cell cell3 = row.createCell(2);
        //设置数据
        cell1.setCellValue("账号");
        cell2.setCellValue("邮箱");
        cell3.setCellValue("年龄");

        for (int i=0;i<emps.size();i++) {
            Row newRow = sheet.createRow(i+1);
            newRow.createCell(0).setCellValue(emps.get(i).getName());
            newRow.createCell(1).setCellValue(emps.get(i).getEmail());
            newRow.createCell(2).setCellValue(emps.get(i).getAge());
        }
        return book;
    }

    @Override
    public void importExcel(InputStream inputStream) throws IOException {
        HSSFWorkbook book = new HSSFWorkbook(inputStream);
        HSSFSheet sheet1 = book.getSheet("Sheet1");
        int lastRowNum = sheet1.getLastRowNum();
        for (int i = 0; i <lastRowNum ; i++) {
            HSSFRow row = sheet1.getRow(i+1);
            Employee e = new Employee();
            e.setName(row.getCell(0).getStringCellValue());
            e.setEmail(row.getCell(1).getStringCellValue());
            System.out.println();
            e.setAge(Double.valueOf(row.getCell(2).getNumericCellValue()).intValue());
            employeeMapper.insert(e);
        }
    }

    @Override
    public List<Employee> listAllSellers() {
        return employeeMapper.listAllSellers();
    }

    /**
     * 获取除了管理员的其他销售人员
     * @return
     */
    @Override
    public List<Employee> listSellersNotAdmin() {
        return employeeMapper.listSellersNotAdmin();
    }

    /**
     * 查询除了超级管理员和旧的销售人员之外的所有销售人员
     * @param selledId
     * @return
     */
    @Override
    public List<Employee> selectSellers(Long selledId) {
        return employeeMapper.selectSellers(selledId);
    }

    /**
     * 查询饼状图的所有的分组的名字
     * @return
     */
    @Override
    public List<Map<String, Object>> selectGroupTypeNames(ChartQueryObject qo) {
        return employeeMapper.selectGroupTypeNames(qo);
    }

    /**
     * 根据销售人的id查询当前销售人的邮箱
     * @param sellerId
     * @return
     */
    @Override
    public String sellerEmailById(Long sellerId) {
        return employeeMapper.sellerEmailById(sellerId);
    }


}
