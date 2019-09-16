package cn.wolfcode.crm.service;

import cn.wolfcode.crm.domain.Customer;
import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.query.ChartQueryObject;
import cn.wolfcode.crm.query.CustomerQueryObject;
import cn.wolfcode.crm.query.EmployeeQueryObject;
import cn.wolfcode.crm.query.QueryObject;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface IEmployeeService {
    void save(Employee employee, Long[] ids);

    void update(Employee employee, Long[] ids);

    void delete(Long id);

    Employee get(Long id);

    List<Employee> listAll();

    // 高级查询分页查询
    PageInfo<Employee> query(EmployeeQueryObject qo);

    void login(String name, String password);

    void batchDelete(Long[] ids);

    Employee getEmpByUsername(String username);

    Workbook exportExcel(EmployeeQueryObject qo);

    void importExcel(InputStream inputStream) throws IOException;

    List<Employee> listAllSellers();

    List<Employee> listSellersNotAdmin();

    List<Employee> selectSellers(Long selledId);

    List<Map<String, Object>> selectGroupTypeNames(ChartQueryObject qo);

    String sellerEmailById(Long sellerId);

}