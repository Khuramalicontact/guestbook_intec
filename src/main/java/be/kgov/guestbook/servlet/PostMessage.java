package be.kgov.guestbook.servlet;

import be.kgov.guestbook.dao.MessageDao;
import be.kgov.guestbook.dao.MessageDaoImpl;
import be.kgov.guestbook.model.Message;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@WebServlet("/postMessage")
public class PostMessage extends HttpServlet {

    MessageDao messageDao;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        Message message = new Message();
        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        message.setDate(sqlDate);
        message.setName(req.getParameter("name"));
        message.setMessage(req.getParameter("message"));

        messageDao.createMessage(message);
        System.out.println("Message got created");

        PrintWriter writer = resp.getWriter();
        writer.println("<html>" +
                "<head><title>POST SERVLET</title></head>" +
                "<body>" +
                "<h1>YOUR MESSAGE WAS POSTED!</h1>"+
                "<a href=\"/guestBookServletExc/guestbook\"><button>Back to Messages</button></a>"+
                "</body>" +
                "</html>");
        writer.close();
        resp.sendRedirect(req.getContextPath());
    }

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            messageDao = new MessageDaoImpl(
                    "jdbc:mariadb://noelvaes.eu/StudentDB",
                    "student",
                    "student123"
            );

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        messageDao.closeConnection();
    }
}
