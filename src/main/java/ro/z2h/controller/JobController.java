package ro.z2h.controller;

import ro.z2h.annotation.MyController;
import ro.z2h.annotation.MyRequestMethod;
import ro.z2h.domain.Job;
import ro.z2h.service.JobServiceImpl;

import java.util.List;

/**
 * Created by Ilai Hawkeye on 11/13/2014.
 */
@MyController(urlPath = "/jobs")
public class JobController {

    @MyRequestMethod(urlPath = "/all")
    public List<Job> getAllJobs() {
//        List<Department> departmentList = new ArrayList<Department>();
//        Department department= new Department();
//        department.setId((long) 1);
//        department.setDepartmentName("Teamnet");
//        departmentList.add(department);
//        Department department2= new Department();
//        department2.setId((long) 2);
//        department2.setDepartmentName("DEPART");
//        departmentList.add(department2);
//        return departmentList;
        JobServiceImpl departmentService = new JobServiceImpl();
        return departmentService.findAllJobs();
    }
}