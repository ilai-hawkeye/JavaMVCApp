package ro.z2h.controller;

import ro.z2h.annotation.MyController;
import ro.z2h.annotation.MyRequestMethod;
import ro.z2h.dao.EmployeeDao;
import ro.z2h.domain.Employee;
import ro.z2h.service.EmployeeSerciveImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dumitru on 11.11.2014.
 */
@MyController(urlPath = "/employees")
public class EmployeeController {

    EmployeeSerciveImpl employeeSercive = new EmployeeSerciveImpl();
    //TODO
    @MyRequestMethod(urlPath = "/all")
    public List<Employee> getAllEmployees() {




//        List<Employee> employees = new ArrayList<Employee>();
//        Employee employee1 = new Employee();
//        employee1.setId((long) 1);
//        employee1.setLastName("Z2H");
//        Employee employee2 = new Employee();
//        employee2.setId((long) 2);
//        employee2.setLastName("AAA");
//        employees.add(employee1);
//        employees.add(employee2);

        return employeeSercive.findAllEmployees();
    }

    @MyRequestMethod(urlPath = "/one")
    public Employee getOneEmployee(String id) {

//        Employee employee1 = new Employee();
//        employee1.setId((long) 1);
//        employee1.setLastName("Z2H");

        return employeeSercive.findOneEmployee(Long.valueOf(id));
    }
    @MyRequestMethod(urlPath = "/delete")
    public void deleteOneEmployee(String id) {



        employeeSercive.deleteOneEmployee(Long.valueOf(id));
    }
}