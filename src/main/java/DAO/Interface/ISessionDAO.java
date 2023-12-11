package DAO.Interface;

import DTO.Session;

import java.sql.SQLException;

public interface ISessionDAO extends IDAO<Session, Integer> {
     Session findByComputerId(int computerId ) throws SQLException;
     Session findByAccountId(int accountId) throws SQLException;
}
