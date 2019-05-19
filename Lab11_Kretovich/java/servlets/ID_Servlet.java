package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@WebServlet(name = "ID_Servlet")
public class ID_Servlet extends HttpServlet {
    final List<String> list = new CopyOnWriteArrayList<String>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter printWriter = response.getWriter();
        printWriter.append(
                "<!DOCTYPE html>" +
                        "<html>" +
                        "<head>" +
                        " <title> Egyptian_King </title>" +
                        "</head>" +
                        "<body>" +
                        "   <form action='"+request.getContextPath()+"/' method='post'>" +
                        " Name : <input type='text' name='name'>" +
                        " <input type='submit' value='Submit'>" +
                        "<form>" +
                        this.viewStrings() +
                        "</body>" +
                        "</html>"

        );
        printWriter.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.list.add(new String(request.getParameter("name")));
        doGet(request,response);
    }

    private String viewStrings() {
        StringBuffer sb = new StringBuffer();
        sb.append("<p>Strings</p>");
        sb.append("<table style='border : 1px solid black'>");
        for (int i = 0; i < list.size(); i++) {
            sb.append("<tr><td style='border : 1px solid black'>").append(list.get(i)).append("</td></tr>");
        }
        sb.append("</table>");
        return sb.toString();
    }


}
