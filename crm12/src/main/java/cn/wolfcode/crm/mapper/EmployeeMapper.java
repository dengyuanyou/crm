package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.query.ChartQueryObject;
import cn.wolfcode.crm.query.EmployeeQueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface EmployeeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Employee record);

    Employee selectByPrimaryKey(Long id);

    List<Employee> selectAll();

    int updateByPrimaryKey(Employee record);


    List<Employee> selectForList(EmployeeQueryObject qo);

    Employee queryByNameAndPassword(@Param("name") String name, @Param("password") String password);

    /**
     * 保存员工和角色的关系
     *
     * @param employeeId
     * @param roleId
     */
    void insertEmpRoleRelation(@Param("employeeId") Long employeeId, @Param("roleId") Long roleId);

    void deleteEmpRoleRelation(Long empId);

    void deleteByBatch(Long[] ids);

    Employee selectByUsername(String username);

    List<Employee> selectEmpForExport(EmployeeQueryObject qo);

    List<Employee> selectEmpByRoleSn(String[] roleSns);

    List<Employee> listAllSellers();

    List<Employee> listSellersNotAdmin();

    List<Employee> selectSellers(Long selledId);

    List<Map<String, Object>> selectGroupTypeNames(ChartQueryObject qo);

    String sellerEmailById(Long sellerId);
}