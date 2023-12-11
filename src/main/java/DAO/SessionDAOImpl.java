package DAO;

import DAO.Interface.ISessionDAO;
import DTO.Session;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class SessionDAOImpl extends BaseDAO implements ISessionDAO {

    @Override
    public Session create(Session session) throws SQLException {
        var preparedStatement = this.prepareStatement("INSERT INTO session (computerID, usingBy, startTime, totalTime, usedTime, usedCost, serviceCost, prepaidAmount) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, session.getComputerID());
        preparedStatement.setObject(2, session.getUsingBy());
        preparedStatement.setTime(3, new java.sql.Time(session.getStartTime().getTime()));
        preparedStatement.setInt(4, session.getTotalTime());
        preparedStatement.setInt(5, session.getUsedTime());
        preparedStatement.setDouble(6, session.getUsedCost());
        preparedStatement.setInt(7, session.getServiceCost());
        preparedStatement.setDouble(8, session.getPrepaidAmount());
        preparedStatement.executeUpdate();
        var resultSet = preparedStatement.getGeneratedKeys();
        if (resultSet.next()) {
            session.setId(resultSet.getInt(1));
        }
        return session;

    }

    @Override
    public Session update(Session session) throws SQLException {
       var preparedStatement = this.prepareStatement("UPDATE session SET computerID = ?, usingBy = ?, startTime = ?, totalTime = ?, usedTime = ?, usedCost = ?, serviceCost = ?, prepaidAmount = ? WHERE id = ?");
        preparedStatement.setInt(1, session.getComputerID());
        preparedStatement.setObject(2, session.getUsingBy());
        preparedStatement.setTimestamp(3, new java.sql.Timestamp(session.getStartTime().getTime()));
        preparedStatement.setInt(4, session.getTotalTime());
        preparedStatement.setInt(5, session.getUsedTime());
        preparedStatement.setDouble(6, session.getUsedCost());
        preparedStatement.setInt(7, session.getServiceCost());
        preparedStatement.setDouble(8, session.getPrepaidAmount());
        preparedStatement.setInt(9, session.getId());
        preparedStatement.executeUpdate();
        return this.findByComputerId(session.getComputerID());
    }

    @Override
    public boolean delete(Integer integer) throws SQLException {
        var preparedStatement = this.prepareStatement("DELETE FROM session WHERE id = ?");
        preparedStatement.setInt(1, integer);
        return preparedStatement.executeUpdate() > 0;
    }

    @Override
    public Session findById(Integer integer) throws SQLException {
        var preparedStatement = this.prepareStatement("SELECT * FROM session WHERE id = ?");
        preparedStatement.setInt(1, integer);
        var resultSet = preparedStatement.executeQuery();
        var list = DBHelper.toList(resultSet, Session.class);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<Session> findAll() throws SQLException {
        var preparedStatement = this.prepareStatement("SELECT * FROM session");
        var resultSet = preparedStatement.executeQuery();
        return DBHelper.toList(resultSet, Session.class);
    }

    @Override
    public Session findByComputerId(int computerId) throws SQLException {
        var preparedStatement = this.prepareStatement("SELECT * FROM session where computerID = ?");
        preparedStatement.setInt(1,computerId);
        var resultSet = preparedStatement.executeQuery();
        var resultList= DBHelper.toList(resultSet, Session.class);
        if (resultList.size() > 0) {
            return resultList.get(0);
        }
        return null;

    }

    @Override
    public Session findByAccountId(int accountId) throws SQLException {
        return null;
    }
}
