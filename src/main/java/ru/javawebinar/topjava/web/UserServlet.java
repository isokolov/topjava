package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class UserServlet extends javax.servlet.http.HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(UserServlet.class);

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        log.debug("redirect to users");

//        request.getRequestDispatcher("/users.jsp").forward(request, response);
        response.sendRedirect("users.jsp");
        log.debug("forward to users");
        request.getRequestDispatcher("/users.jsp").forward(request, response);
    }
}
