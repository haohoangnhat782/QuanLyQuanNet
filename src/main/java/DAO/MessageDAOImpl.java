package DAO;

import DAO.Interface.IMessageDAO;
import DTO.Message;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class MessageDAOImpl extends BaseDAO implements IMessageDAO{
    @Override
    public Message create(Message message) throws SQLException {
        PreparedStatement preparedStatement = this.prepareStatement("INSERT INTO Message (sessionId, content, fromType, createdAt) VALUES (?, ?, ?, ?)");
        preparedStatement.setInt(1, message.getSessionId());
        preparedStatement.setString(2, message.getContent());
        preparedStatement.setInt(3, message.getFromType().ordinal());
        preparedStatement.setTimestamp(4, new java.sql.Timestamp(message.getCreatedAt().getTime()));
        preparedStatement.executeUpdate();
        return message;
    }

    @Override
    public Message update(Message message) throws SQLException {
        return null;
    }

    @Override
    public boolean delete(Integer integer) throws SQLException {
        PreparedStatement preparedStatement = this.prepareStatement("DELETE FROM Message WHERE id = ?");
        preparedStatement.setInt(1, integer);
        return preparedStatement.executeUpdate() > 0;

    }

    @Override
    public Message findById(Integer integer) throws SQLException {
        return null;
    }

    @Override
    public List<Message> findAll() throws SQLException {
        return null;
    }
    public List<Message> findAllBySessionId(int sessionId) throws SQLException {
        PreparedStatement preparedStatement = this.prepareStatement("SELECT * FROM Message WHERE sessionId = ?");
        preparedStatement.setInt(1, sessionId);
        return DBHelper.toList(preparedStatement.executeQuery(), Message.class);
    }
}
