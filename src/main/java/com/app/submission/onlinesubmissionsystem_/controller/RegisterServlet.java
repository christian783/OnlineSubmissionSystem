package com.app.submission.onlinesubmissionsystem_.controller;

import com.app.submission.onlinesubmissionsystem_.dao.UserDAO;
import com.app.submission.onlinesubmissionsystem_.model.Role;
import com.app.submission.onlinesubmissionsystem_.model.User;
// import util.HibernateUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || role.isEmpty()) {
            response.sendRedirect("register.jsp?error=MissingFields");
            return;
        }

        UserDAO userDAO = new UserDAO();
        if (userDAO.getUserByEmail(email) != null) {
            response.sendRedirect("register.jsp?error=EmailExists");
            return;
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password); // Consider hashing this
        user.setRole(Role.valueOf(role));

        userDAO.saveUser(user);
        response.sendRedirect("login.jsp?success=RegistrationComplete");
    }
}
