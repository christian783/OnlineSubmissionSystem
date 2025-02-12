package com.app.submission.onlinesubmissionsystem_.controller;

import com.app.submission.onlinesubmissionsystem_.dao.AssignmentDAO;
import com.app.submission.onlinesubmissionsystem_.dao.SubmissionDAO;
import com.app.submission.onlinesubmissionsystem_.model.Assignment;
import com.app.submission.onlinesubmissionsystem_.model.Submission;
import com.app.submission.onlinesubmissionsystem_.model.User;
//import util.HibernateUtil;

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
import java.nio.file.Paths;
import java.time.LocalDateTime;

@WebServlet("/UploadSubmissionServlet")
@MultipartConfig
public class UploadSubmissionServlet extends HttpServlet {
    private static final String UPLOAD_DIR = "uploads";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null || user.getRole() != com.app.submission.onlinesubmissionsystem_.model.Role.STUDENT) {
            response.sendRedirect("login.jsp");
            return;
        }

        Long assignmentId = Long.parseLong(request.getParameter("assignmentId"));
        Part filePart = request.getPart("file");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir();

        String filePath = uploadPath + File.separator + fileName;
        filePart.write(filePath);

        SubmissionDAO submissionDAO = new SubmissionDAO();
        Submission existingSubmission = submissionDAO.getSubmissionByStudentAndAssignment(user.getId(), assignmentId);

        if (existingSubmission != null) {
            submissionDAO.deleteSubmission(existingSubmission.getId());
        }

        AssignmentDAO assignmentDAO = new AssignmentDAO();
        Assignment assignment = assignmentDAO.getAssignmentById(assignmentId);

        Submission submission = new Submission();
        submission.setStudent(user);
        submission.setAssignment(assignment);
        submission.setFilePath("uploads/" + fileName);
        submission.setSubmittedAt(LocalDateTime.now());

        submissionDAO.saveSubmission(submission);

        response.sendRedirect("studentDashboard.jsp");
    }
}
