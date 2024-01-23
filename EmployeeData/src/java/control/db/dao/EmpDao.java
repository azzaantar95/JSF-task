/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.db.dao;

import java.sql.*;
import control.db.DbConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import model.Employee;

/**
 *
 * @author AZZA
 */
public class EmpDao extends DbConnection implements DaoList<Employee> {

    // singletone design pattern to have only one instnace from connection database
    private static EmpDao empDao;

    // private constructor 
    private EmpDao() {
    }

    // this static method to get only one object from this class
    public static EmpDao getInstance() {
        if (empDao == null) {
            empDao = new EmpDao();
            // System.err.println("dooooone");
        }

        return empDao;
    }

    private Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

    @Override
    public String getDataById() throws Exception {

        Connection connection = null;

        Employee obj_emp = null;

        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
        String field_Emp_Id = params.get("action");
        try {
            connection = getConnection();
            Statement st = connection.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT * FROM employee WHERE employeeId=" + field_Emp_Id);

            while (resultSet.next()) {
                obj_emp = new Employee();

                obj_emp.setEmpId(resultSet.getInt("employeeId"));
                obj_emp.setEmpCode(resultSet.getString("employeeCode"));
                obj_emp.setEmpName(resultSet.getString("employeeName"));
                obj_emp.setEmpAddress(resultSet.getString("employeeAddress"));
                obj_emp.setEmpEmail(resultSet.getString("employeeEmail"));

                sessionMap.put("editEmployee", obj_emp);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return "view/edit.xhtml?faces-redirect=true";
    }

    // business logic of get method for all employees from database 
    @Override
    public List<Employee> getAllData() throws Exception {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Employee employeeBean = null;
        List<Employee> listOfEmployees = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            String sqlQuery = "SELECT * FROM employee";
            preparedStatement = connection.prepareStatement(sqlQuery);
            resultSet = preparedStatement.executeQuery();

            listOfEmployees = new ArrayList<>();
            while (resultSet.next()) {
                employeeBean = new Employee();

                employeeBean.setEmpId(resultSet.getInt("employeeId"));
                employeeBean.setEmpCode(resultSet.getString("employeeCode"));
                employeeBean.setEmpName(resultSet.getString("employeeName"));
                employeeBean.setEmpAddress(resultSet.getString("employeeAddress"));
                employeeBean.setEmpEmail(resultSet.getString("employeeEmail"));

                listOfEmployees.add(employeeBean);
            }
        } catch (Exception ex) {
            Logger.getLogger(EmpDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listOfEmployees;
    }

    // business logic of insert method a new employee into database 
    @Override
    public int insertData(Employee emp) throws Exception {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int count = 0;

        try {
            connection = getConnection();
            String sqlQuery = "INSERT INTO employee(employeeCode,employeeName,employeeAddress,employeeEmail)VALUES(?,?,?,?)";
            preparedStatement = connection.prepareStatement(sqlQuery);

            preparedStatement.setString(1, emp.getEmpCode());
            preparedStatement.setString(2, emp.getEmpName());
            preparedStatement.setString(3, emp.getEmpAddress());
            preparedStatement.setString(4, emp.getEmpEmail());

            count = preparedStatement.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EmpDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            preparedStatement.close();
            closeConnection(connection);
        }

        return count;
    }

    // business logic of update method 
    @Override
    public int updateData(Employee emp) {

        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
        String update_sl_no = params.get("update_sl_no");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int count = 0;

        try {
            connection = getConnection();
            String sqlQuery = "UPDATE employee SET employeeCode=?,employeeName=?,employeeAddress=?,employeeEmail=? WHERE employeeCode=?";
            preparedStatement = connection.prepareStatement(sqlQuery);

            preparedStatement.setString(1, emp.getEmpCode());
            preparedStatement.setString(2, emp.getEmpName());
            preparedStatement.setString(3, emp.getEmpAddress());
            preparedStatement.setString(4, emp.getEmpEmail());

            preparedStatement.setString(5, emp.getEmpCode());

            count = preparedStatement.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EmpDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

        }

        return count;
    }

    @Override
    public int deleteData(Employee emp) throws Exception {

        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
        String emp_Id = params.get("action");
        Connection connection = null;

        PreparedStatement preparedStatement = null;

        int count = 0;

        try {
            connection = getConnection();
            String sqlQuery = "DELETE FROM employee WHERE employeeCode=?";
            preparedStatement = connection.prepareStatement(sqlQuery);

            preparedStatement.setString(1, emp_Id);

            count = preparedStatement.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EmpDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

        }
        return count;
    }

}
