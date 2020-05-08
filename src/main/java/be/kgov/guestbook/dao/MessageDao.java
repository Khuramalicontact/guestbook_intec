package be.kgov.guestbook.dao;

import be.kgov.guestbook.model.Message;

import java.sql.Connection;
import java.util.List;

public interface MessageDao {

    List<Message> getAllMessages();

    void createMessage(Message message);

    Connection getConnection(String url, String user, String password);

    void closeConnection();
}
