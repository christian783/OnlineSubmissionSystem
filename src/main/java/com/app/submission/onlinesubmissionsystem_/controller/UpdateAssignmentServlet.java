package com.app.submission.onlinesubmissionsystem_.controller;

import com.app.submission.onlinesubmissionsystem_.dao.AssignmentDAO;
import com.app.submission.onlinesubmissionsystem_.model.Assignment;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/UpdateAssignmentServlet")
public class UpdateAssignmentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Get assignment details from form
            int assignmentId = Integer.parseInt(request.getParameter("assignmentId"));
            String title = request.getParameter("title");
            String deadlineStr = request.getParameter("deadline");

            // Convert deadline string to LocalDateTime
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime deadline = LocalDateTime.parse(deadlineStr, formatter);

            // Fetch existing assignment and update details
            AssignmentDAO assignmentDAO = new AssignmentDAO();
            Assignment assignment = assignmentDAO.getAssignmentById((long) assignmentId);
            if (assignment != null) {
                assignment.setTitle(title);
                assignment.setDeadline(LocalDate.from(deadline));
                assignmentDAO.updateAssignment(assignment);
            }

            // Redirect back to instructor dashboard
            response.sendRedirect("instructorDashboard.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp"); // Redirect to an error page if something goes wrong
        }
    }
}
