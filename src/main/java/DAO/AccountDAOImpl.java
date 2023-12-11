package DAO;

import DAO.Interface.IAccountDAO;
import lombok.NoArgsConstructor;
import DTO.Account;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@NoArgsConstructor
public class AccountDAOImpl extends BaseDAO implements IAccountDAO {

    public static AccountDAOImpl getInstance(){
        return new AccountDAOImpl();
    }

    public Account create(Account account) throws SQLException {
        var preparedStatement = DBHelper
                .getConnection()
                .prepareStatement("INSERT INTO account (username, password, balance, role, createdAt, deletedAt) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, account.getUsername());
        preparedStatement.setString(2, account.getPassword());
        preparedStatement.setDouble(3, account.getBalance());
        preparedStatement.setInt(4, account.getRole().ordinal());
        preparedStatement.setDate(5, new java.sql.Date(new java.util.Date().getTime()));
        preparedStatement.setDate(6, null);
        var result = preparedStatement.executeUpdate();
        if (result > 0) {
            var resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                var newId = resultSet.getInt(1);
                return this.findById(newId);
            }
        }
        return null;

    }

    public Account update(Account account) throws SQLException {
        var preparedStatement = this.prepareStatement("UPDATE account  SET " +
                " username = ?, " +
                " password = ?, " +
                " balance = ?, " +
                " role = ?, " +
                " createdAt = ? " +
                " WHERE id = ?");
        preparedStatement.setString(1, account.getUsername());
        preparedStatement.setString(2, account.getPassword());
        preparedStatement.setDouble(3, account.getBalance());
        // get int from enum
        preparedStatement.setInt(4, account.getRole().ordinal());
        preparedStatement.setDate(5, new java.sql.Date(account.getCreatedAt().getTime()));
        preparedStatement.setInt(6, account.getId());
        var result = preparedStatement.executeUpdate();
        preparedStatement.close();
        return result > 0 ? this.findById(account.getId()) : null;
    }

    public boolean delete(Integer integer) throws SQLException {
        var preparedStatement = this.prepareStatement("Update account SET deletedAt = ? WHERE id = ?");
        preparedStatement.setDate(1, new java.sql.Date(new java.util.Date().getTime()));
        preparedStatement.setInt(2, integer);
        var result = preparedStatement.executeUpdate();
        preparedStatement.close();
        return result > 0;
    }

    public Account findById(Integer id) throws SQLException {
        var preparedStatement = this.prepareStatement("SELECT * FROM account a WHERE a.id = ? and a.deletedAt is null");
        preparedStatement.setInt(1, id);
        var resultSet = preparedStatement.executeQuery();
        var accounts = DBHelper.toList(resultSet, Account.class);
        preparedStatement.close();
        return accounts.size() > 0 ? accounts.get(0) : null;
    }


    public List<Account> findAll() throws SQLException {
        var statement = this.createStatement();
        var resultSet = statement.executeQuery("SELECT * FROM account a WHERE a.deletedAt is null");
        var accounts = DBHelper.toList(resultSet, Account.class);
        statement.close();
        return accounts;
    }
    public List<Account> findAll(Account.Role beforeRole) throws SQLException {
        var statement = this.createStatement();
        var resultSet = statement.executeQuery("SELECT * FROM account a WHERE a.deletedAt is null and a.role > "+beforeRole.ordinal());
        var accounts = DBHelper.toList(resultSet, Account.class);
        statement.close();
        return accounts;
    }
    @Override
    public Account findByUsername(String username) throws SQLException {

        var statement = this.prepareStatement("SELECT * FROM account a WHERE a.username = ? and a.deletedAt is null");
        statement.setString(1, username);
        var resultSet = statement.executeQuery();
        var accounts = DBHelper.toList(resultSet, Account.class);
        return accounts.size() > 0 ? accounts.get(0) : null;
    }
}

