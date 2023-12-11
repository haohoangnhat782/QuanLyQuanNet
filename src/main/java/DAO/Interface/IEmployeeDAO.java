 package DAO.Interface;

import DTO.Employee;

import java.sql.SQLException;

public interface IEmployeeDAO extends IDAO<Employee, Integer> {
    public Employee findByAccountID(Integer id) throws SQLException;
}

