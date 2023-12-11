package BUS;

import DAO.Interface.IMessageDAO;
import lombok.Setter;
import DTO.Message;

import java.sql.SQLException;
import java.util.List;

public class MessageBUS {
    @Setter
    private IMessageDAO messageDAOImpl;

    public Message create(Message message) throws SQLException {
        message.setCreatedAt(new java.util.Date());
        return messageDAOImpl.create(message);
    }
    public List<Message> findAllBySessionId(int sessionId) throws SQLException {
        return messageDAOImpl.findAllBySessionId(sessionId);
    }
}
