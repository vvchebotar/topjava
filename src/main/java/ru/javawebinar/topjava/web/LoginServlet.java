package ru.javawebinar.topjava.web;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String select = request.getParameter("select");


        switch (select == null ? "all" : select) {
            case "1":
                SecurityUtil.setAuthUserId(1);
                response.sendRedirect("index.html");
                break;
            case "2":
                SecurityUtil.setAuthUserId(2);
                response.sendRedirect("index.html");
                break;
            case "all":
            default:
                response.sendRedirect("index.html");
        }
    }
}
