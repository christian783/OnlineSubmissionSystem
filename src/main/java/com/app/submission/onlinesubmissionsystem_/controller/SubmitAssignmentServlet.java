package com.app.submission.onlinesubmissionsystem_.controller;

import com.app.submission.onlinesubmissionsystem_.dao.AssignmentDAO;
import com.app.submission.onlinesubmissionsystem_.dao.SubmissionDAO;
import com.app.submission.onlinesubmissionsystem_.model.Assignment;
import com.app.submission.onlinesubmissionsystem_.model.Submission;
import com.app.submission.onlinesubmissionsystem_.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/SubmitAssignmentServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,       // 10MB
        maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class SubmitAssignmentServlet extends HttpServlet {
    private static final String UPLOAD_DIR = "submissions";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null || user.getRole() != com.app.submission.onlinesubmissionsystem_.model.Role.STUDENT) {
            response.sendRedirect("login.jsp");
            return;
        }

        Long assignmentId = Long.parseLong(request.getParameter("assignmentId"));
        Part filePart = request.getPart("file");
        String fileName = filePart.getSubmittedFileName();

        // Save file on server
        String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir();

        String filePath = uploadPath + File.separator + fileName;
        filePart.write(filePath);

        // Save submission to DB
        AssignmentDAO assignmentDAO = new AssignmentDAO();
        Assignment assignment = assignmentDAO.getAssignmentById(assignmentId);

        Submission submission = new Submission();
        submission.setAssignment(assignment);
        submission.setStudent(user);
        submission.setFilePath(filePath);
        submission.setSubmittedAt(LocalDateTime.now());

        SubmissionDAO submissionDAO = new SubmissionDAO();
        submissionDAO.saveSubmission(submission);

        response.sendRedirect("studentDashboard.jsp");
    }
}
