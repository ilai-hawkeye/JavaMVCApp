package ro.z2h.controller;

import ro.z2h.annotation.MyController;
import ro.z2h.annotation.MyRequestMethod;
import ro.z2h.domain.Department;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dumitru on 11.11.2014.
 */
@MyController(urlPath = "/departments")
public class DepartmentController {

    @MyRequestMethod(urlPath = "/all")
    public List<Department> getAllDepartments() {
        List<Department> departmentList = new ArrayList<Department>();
        Department department= new Department();
        department.setId((long) 1);
        department.setDepartmentName("Teamnet");
        departmentList.add(department);
        Department department2= new Department();
        department2.setId((long) 2);
        department2.setDepartmentName("DEPART");
        departmentList.add(department2);
        return departmentList;
    }
}