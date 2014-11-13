package ro.z2h.service;

import ro.z2h.dao.DepartmentDao;
import ro.z2h.dao.EmployeeDao;
import ro.z2h.domain.Department;
import ro.z2h.utils.DatabaseManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Ilai Hawkeye on 11/13/2014.
 */
public class DepartmentServiceImpl implements DepartmentService {

    private final String username ="zth_02";
    private final String password ="passw0rd";
    @Override
    public List<Department> findAllDepartments() {

        DepartmentDao departmentDao = new DepartmentDao();
        Connection conn = DatabaseManager.getConnection(username, password);
        try {
            return departmentDao.getAllDepartments(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
