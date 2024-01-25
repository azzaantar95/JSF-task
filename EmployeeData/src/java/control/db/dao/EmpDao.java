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

    // set the data of the employee in a session
   public Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

    @Override
    public String getDataById(int employeeId) throws Exception {

        Connection connection = null;

        Employee obj_emp = null;
        try {
            connection = getConnection();
            Statement st = connection.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT * FROM employee WHERE employeeId=" + employeeId);

            if (resultSet != null) {

                resultSet.next();

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
        return "edit.xhtml?faces-redirect=true";
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
    public String insertData(Employee emp) throws Exception {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            String sqlQuery = "INSERT INTO employee(employeeCode,employeeName,employeeAddress,employeeEmail)VALUES(?,?,?,?)";
            preparedStatement = connection.prepareStatement(sqlQuery);

            preparedStatement.setString(1, emp.getEmpCode());
            preparedStatement.setString(2, emp.getEmpName());
            preparedStatement.setString(3, emp.getEmpAddress());
            preparedStatement.setString(4, emp.getEmpEmail());

            preparedStatement.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EmpDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "index.xhtml?faces-redirect=true";
    }

    // business logic of update method 
    @Override
    public String updateData(Employee emp) {

      
        Connection connection = null;
        PreparedStatement preparedStatement = null;
     
        try {
            connection = getConnection();
            String sqlQuery = "UPDATE employee SET employeeCode=?,employeeName=?,employeeAddress=?,employeeEmail=? WHERE employeeId=?";
            preparedStatement = connection.prepareStatement(sqlQuery);

            preparedStatement.setString(1, emp.getEmpCode());
            preparedStatement.setString(2, emp.getEmpName());
            preparedStatement.setString(3, emp.getEmpAddress());
            preparedStatement.setString(4, emp.getEmpEmail());

            preparedStatement.setInt(5, emp.getEmpId());

            preparedStatement.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EmpDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            

        }
      //  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("editEmployee", null);

        return "index.xhtml?faces-redirect=true";
    }

    // to delete employee from database based on its id
    @Override
    public String deleteData(int employeeId) throws Exception {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();

            String sqlQuery = "DELETE FROM employee WHERE employeeId=" + employeeId;
            preparedStatement = connection.prepareStatement(sqlQuery);

            preparedStatement.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EmpDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            connection.close();
            preparedStatement.close();
        }
        return "index.xhtml?faces-redirect=true";
    }
    
    

}
