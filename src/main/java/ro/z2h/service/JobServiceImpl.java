package ro.z2h.service;

import ro.z2h.dao.EmployeeDao;
import ro.z2h.dao.JobDao;
import ro.z2h.domain.Employee;
import ro.z2h.domain.Job;
import ro.z2h.utils.DatabaseManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Ilai Hawkeye on 11/13/2014.
 */
public class JobServiceImpl implements JobService {

    private final String username ="zth_02";
    private final String password ="passw0rd";

    @Override
    public List<Job> findAllJobs() {

        JobDao jobDao = new JobDao();
        Connection conn = DatabaseManager.getConnection(username, password);
        try {
            return jobDao.getAllJobs(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
}return null;}}
