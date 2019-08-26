package main.java.servlet;

import main.java.server.Request;
import main.java.server.Response;

public interface Servlet {
     void service(Request request, Response response);
}
