package main.java.servlet;

import main.java.server.Request;
import main.java.server.Response;

public class LoginServlet implements Servlet {
    @Override
    public void service(Request request, Response response) {
        response.print("<html>");
        response.print("<head>");
        response.print("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
        response.print("<title>");
        response.print("服务器响应成功");
        response.print("</title>");
        response.print("</head>");
        response.print("<body>");
        response.print("欢迎回来"+request.getParameter("username"));
        response.print("</body>");
        response.print("</html>");
    }
}
