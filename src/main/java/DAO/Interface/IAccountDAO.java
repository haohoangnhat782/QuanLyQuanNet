package DAO.Interface;

import DTO.Account;

import java.sql.SQLException;
import java.util.List;

public interface  IAccountDAO extends IDAO<Account, Integer> {
    public Account findByUsername(String username) throws SQLException;
    public List<Account> findAll(Account.Role beforeRole) throws SQLException;
}

