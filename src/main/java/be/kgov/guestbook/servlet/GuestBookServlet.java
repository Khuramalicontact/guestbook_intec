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
import java.util.List;
import java.util.ListIterator;

@WebServlet("/guestbook")
public class GuestBookServlet extends HttpServlet {

    private MessageDao messageDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        List<Message> messages = messageDao.getAllMessages();

        PrintWriter writer = resp.getWriter();

        writer.println("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Message Chat service</title>\n" +"<LINK REL=\"StyleSheet\" HREF=\"<%=request.getContextPath()%>/util/CSS/Style.css\" TYPE=\"text/css\">"+
                "    <link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css\" integrity=\"sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh\" crossorigin=\"anonymous\">"+
                "</head>");

        writer.println("<body>\n" +
                "   <div class=\"container\">"+
                "       <h1 class=\"text-center\">Welcome to the Guestbook app!</h1>\n" +
                "       <h2 class=\"text-center\">Messages from DB : </h2>\n");
        writer.println("<div class=\"row mb-3\" >");
             writer.println("<div class=\"col-md-12 pt-2 pb-2 border border-success\">");
             writer.println("<form method=\"post\" action=\"postMessage\">");
                writer.println("  <div class=\"form-group\">\n" +
                        "    <label for=\"inputName\">Your name:</label>\n" +
                        "    <input name=\"name\" type=\"text\" class=\"form-control\" id=\"inputName\" placeholder=\"Enter your name please\">\n" +
                        "    <label for=\"textArea\">Example textarea</label>\n" +
                        "    <textarea name=\"message\" class=\"form-control\" id=\"textArea\" placeholder=\"Enter your message here ...\" rows=\"3\"></textarea>" +
                        "    <button type=\"submit\" class=\"btn btn-success btn-block text-center mt-3 mb-2 bd-highlight\">Click to POST message</button>" +
                        "  </div>");
        writer.println("</form>");
        writer.println("</div>");
        writer.println("</div>");
        writer.println("<div class=\"row\" style=\"border-top: 1px solid black; border-left: 1px solid black; border-right: 1px solid black\">");
        writer.println("<div class=\"col-md-1 font-weight-bold\">");
        writer.println("ID");
        writer.println("</div>");
        writer.println("<div class=\"col-md-2 font-weight-bold\">");
        writer.println("Date");
        writer.println("</div>");
        writer.println("<div class=\"col-md-2 font-weight-bold\">");
        writer.println("User");
        writer.println("</div>");
        writer.println("<div class=\"col-md-7 font-weight-bold\">");
        writer.println("Message");
        writer.println("</div>");
        writer.println("</div>");

        ListIterator li = messages.listIterator(messages.size());

        while(li.hasPrevious()){
            Message msg = (Message) li.previous();

            writer.println("<div class=\"row\" style=\"border-top: 1px solid black; border-left: 1px solid black; border-right: 1px solid black\">");
            writer.println("<div class=\"col-md-1\">");
            writer.println(msg.getId());
            writer.println("</div>");
            writer.println("<div class=\"col-md-2\">");
            writer.println(msg.getDate());
            writer.println("</div>");
            writer.println("<div class=\"col-md-2\">");
            writer.println(msg.getName());
            writer.println("</div>");
            writer.println("<div class=\"col-md-7\">");
            writer.println(msg.getMessage());
            writer.println("</div>");
            writer.println("</div>");
        }

        writer.println("</div></body>");

        writer.close();
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
}
