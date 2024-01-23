/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.db.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ApplicationScoped;

import javax.faces.bean.ViewScoped;
import model.Employee;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author AZZA
 */
@ManagedBean(name = "empBean")
@RequestScoped
public class EmpBean {

    // instead of write attributes of employee, inject employee object hereS
    private Employee employee = new Employee();

    private List<Employee> employeesList = new ArrayList<>();

    /**
     * Creates a new instance of EmpBean
     */
    public EmpBean() {
    }

    // setters and getters for injected employee object
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    // get all employees from the database
    public void loadData() throws Exception {
        employeesList = EmpDao.getInstance().getAllData();

    }

    public void loadDataByID() throws Exception {
        System.out.println("hi from lood");
         EmpDao.getInstance().getDataById();

    }

    //inset new employee to database
    public void insertData() throws Exception {
        EmpDao.getInstance().insertData(employee);
    }

    // edit existed employee in the database
    public void updateData() throws Exception {
        EmpDao.getInstance().updateData(employee);
    }

    public void deleteData() throws Exception {

        System.out.println("sssssssssssssssssss");

        EmpDao.getInstance().deleteData(employee);
        System.out.println(employee.getEmpCode());

    }

    // setters and getters for list of employees which is retrieved from the database
    public List<Employee> getEmployeesList() {
        return employeesList;
    }

    public void setEmployeesList(List<Employee> employeesList) {
        this.employeesList = employeesList;
    }

    ///////////////
}
