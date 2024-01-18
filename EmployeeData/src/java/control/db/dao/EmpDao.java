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
import java.util.logging.Level;
import java.util.logging.Logger;
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

}
