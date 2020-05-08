package be.kgov.guestbook.dao;

import be.kgov.guestbook.model.Message;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDaoImpl implements MessageDao {

    private String url;

    private String user;

    private String password;

    private Connection connection;

    public MessageDaoImpl(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.connection = getConnection(url, user, password);
    }

    @Override
    public List<Message> getAllMessages() {
        List<Message> messages = new ArrayList<>();
        try(PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM StudentDB.GuestBook")) {
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    Message message = new Message();
                    message.setId(resultSet.getInt(1));
                    message.setDate(resultSet.getDate(2));
                    message.setName(resultSet.getString(3));
                    message.setMessage(resultSet.getString(4));

                    messages.add(message);
                }
            }

        }catch (SQLException se) {
            System.out.println(se.getMessage());
        }

        return messages;
    }

    @Override
    public void createMessage(Message message) {
        try(PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            "INSERT INTO StudentDB.GuestBook(Date, Name, Message) " +
                                    "VALUES(?,?,?)")
        ){
            preparedStatement.setDate(1, message.getDate());
            preparedStatement.setString(2, message.getName());
            preparedStatement.setString(3, message.getMessage());
            preparedStatement.execute();

        }catch (SQLException se){
            System.out.println(se.getMessage());
        }

    }

    @Override
    public Connection getConnection(String url, String user, String password) {
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
