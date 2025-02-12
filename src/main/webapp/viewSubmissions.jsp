<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.app.submission.onlinesubmissionsystem_.dao.SubmissionDAO, com.app.submission.onlinesubmissionsystem_.dao.AssignmentDAO, com.app.submission.onlinesubmissionsystem_.model.Submission, com.app.submission.onlinesubmissionsystem_.model.Assignment, java.util.List" %>
<%@ page import="com.app.submission.onlinesubmissionsystem_.model.User" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null || user.getRole() != com.app.submission.onlinesubmissionsystem_.model.Role.INSTRUCTOR) {
        response.sendRedirect("login.jsp");
    }

    String assignmentId = request.getParameter("assignmentId");
    AssignmentDAO assignmentDAO = new AssignmentDAO();
    SubmissionDAO submissionDAO = new SubmissionDAO();

    Assignment assignment = assignmentDAO.getAssignmentById(Long.parseLong(assignmentId));
    List<Submission> submissions = submissionDAO.getSubmissionsByAssignment(Long.parseLong(assignmentId));
%>
<!DOCTYPE html>
<html>
<head>
    <title>View Submissions</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h2>Submissions for: <%= assignment.getTitle() %></h2>
    <table class="table">
        <thead>
        <tr>
            <th>Student</th>
            <th>Submitted At</th>
            <th>Status</th>
            <th>File</th>
        </tr>
        </thead>
        <tbody>
        <% for (Submission submission : submissions) { %>
        <tr>
            <td><%= submission.getStudent().getName() %></td>
            <td><%= submission.getSubmittedAt() %></td>
            <td>
                <% if (submission.getSubmittedAt().isBefore(assignment.getDeadline().atStartOfDay())) { %>
                <span class="badge bg-success">On Time</span>
                <% } else { %>
                <span class="badge bg-danger">Late</span>
                <% } %>
            </td>

            <td>
                <a href="<%= submission.getFilePath() %>" download class="btn btn-primary">Download</a>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
    <a href="instructorDashboard.jsp" class="btn btn-secondary">Back</a>
</div>
</body>
</html>
