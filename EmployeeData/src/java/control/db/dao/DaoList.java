/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.db.dao;

import java.util.List;

/**
 *
 * @author AZZA
 */
// interface for three abstract methods get, insert, update
public interface DaoList<T> {

    public List<T> getAllData() throws Exception;
    
    public String getDataById(int employeeId) throws Exception;

    public String insertData(T t) throws Exception;

    public String updateData(T t) throws Exception;

    public String deleteData(int employeeId) throws Exception;
}
