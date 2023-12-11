package DAO;

import DAO.Interface.IEmployeeDAO;
import DTO.Employee;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class EmployeeDAOImpl extends BaseDAO implements IEmployeeDAO{
    @Override
    public Employee findByAccountID(Integer id) throws SQLException {
        var preparedStatement = this.prepareStatement("SELECT * FROM employee WHERE accountID = ?");
        preparedStatement.setInt(1, id);
        var resultSet = preparedStatement.executeQuery();
        var employees = DBHelper.toList(resultSet, Employee.class);
        preparedStatement.close();
        return employees.size() > 0 ? employees.get(0) : null;

    }
    @Override
    public Employee create(Employee employee) throws SQLException {

        try(var  preparedStatement=this.prepareStatement("INSERT INTO employee (name, accountID, salaryPerHour, phoneNumber, address, otherInformation, createdAt, deletedAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1,employee.getName());
            preparedStatement.setObject(2,employee.getAccountID());
            preparedStatement.setInt(3,employee.getSalaryPerHour());
            preparedStatement.setString(4, employee.getPhoneNumber());
            preparedStatement.setString(5, employee.getAddress());
            preparedStatement.setString(6,employee.getOtherInformation());
            preparedStatement.setDate(7,new  java.sql.Date(employee.getCreatedAt().getTime()));
            preparedStatement.setDate(8, null);
            preparedStatement.executeUpdate();
            var resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                employee.setId(resultSet.getInt(1));
            }
            return employee;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public Employee update(Employee employee) throws SQLException {
        var preparedStatement=this.prepareStatement("UPDATE employee SET name = ?, accountID = ?, salaryPerHour = ?, phoneNumber = ?, address = ?, otherInformation = ?, createdAt = ? WHERE id = ?");
        preparedStatement.setString(1,employee.getName());
        preparedStatement.setObject(2,employee.getAccountID());
        preparedStatement.setInt(3,employee.getSalaryPerHour());
        preparedStatement.setString(4, employee.getPhoneNumber());
        preparedStatement.setString(5, employee.getAddress());
        preparedStatement.setString(6,employee.getOtherInformation());
        preparedStatement.setDate(7,new  java.sql.Date(employee.getCreatedAt().getTime()));
        preparedStatement.setInt(8,employee.getId());
        var result=preparedStatement.executeUpdate();
        preparedStatement.close();
        return result>0?this.findById(employee.getId()):null;
    }
    @Override
    public boolean delete(Integer integer) throws SQLException {
        var preparedStatement = this.prepareStatement("UPDATE employee SET deletedAt = ? WHERE id = ?");
        preparedStatement.setDate(1, new java.sql.Date(new java.util.Date().getTime()));
        preparedStatement.setInt(2, integer);
        var result = preparedStatement.executeUpdate();
        preparedStatement.close();
        return result > 0;
    }

    @Override
    public Employee findById(Integer id) throws SQLException {
        var preparedStatement=this.prepareStatement("SELECT * FROM employee a WHERE a.id = ? and a.deletedAt is null");
        preparedStatement.setInt(1, id);
        var resultSet = preparedStatement.executeQuery();
        var list = DBHelper.toList(resultSet, Employee.class);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<Employee> findAll() throws SQLException {
        var statement = this.createStatement();
        var resultSet = statement.executeQuery("SELECT * FROM employee a WHERE a.deletedAt is null");
        var employees = DBHelper.toList(resultSet, Employee.class);
        statement.close();
        return employees;
    }

}
