/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.db.dao;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ApplicationScoped;

import model.Employee;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;


/**
 *
 * @author AZZA
 */

@ManagedBean

@SessionScoped
public class EmpBean {

    // instead of write attributes of employee, inject employee object hereS
    private Employee employee = new Employee();

    private List<Employee> employeesList = new ArrayList<>();

    private int index = 1;

    public int getIndex() {
        return index++;
    }

    public void setIndex(int index) {
        this.index = index;
    }

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

    public String loadDataByID(int employeeId) throws Exception {
        System.out.println("hi from getdataby Id");
        return EmpDao.getInstance().getDataById(employeeId);

    }

    //inset new employee to database
    public String insertData(Employee employee) throws Exception {
        return EmpDao.getInstance().insertData(employee);
    }

    // edit existed employee in the database
    public String updateData(Employee employee) throws Exception {
        return EmpDao.getInstance().updateData(employee);
    }

    public String deleteData(int employeeId) throws Exception {

        return EmpDao.getInstance().deleteData(employeeId);

    }

    // setters and getters for list of employees which is retrieved from the database
    public List<Employee> getEmployeesList() {
        return employeesList;
    }

    public void setEmployeesList(List<Employee> employeesList) {
        this.employeesList = employeesList;
    }

    public String goBack() {

       // FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("editEmployee", null);
        return "index?faces-redirect=true";
    }
}
