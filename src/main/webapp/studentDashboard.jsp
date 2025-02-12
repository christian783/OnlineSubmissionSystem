<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List, com.app.submission.onlinesubmissionsystem_.model.Assignment, com.app.submission.onlinesubmissionsystem_.model.Submission, com.app.submission.onlinesubmissionsystem_.dao.AssignmentDAO, com.app.submission.onlinesubmissionsystem_.dao.SubmissionDAO, com.app.submission.onlinesubmissionsystem_.model.User" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null || user.getRole() != com.app.submission.onlinesubmissionsystem_.model.Role.STUDENT) {
        response.sendRedirect("login.jsp");
        return;
    }

    AssignmentDAO assignmentDAO = new AssignmentDAO();
    SubmissionDAO submissionDAO = new SubmissionDAO();
    List<Assignment> assignments = assignmentDAO.getAllAssignments();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student Dashboard</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h2>Student Dashboard</h2>
    <p>Welcome, <strong><%= user.getName() %></strong>!</p>

    <h3 class="mt-4">Available Assignments</h3>
    <table class="table table-bordered">
        <thead class="table-dark">
        <tr>
            <th>Title</th>
            <th>Deadline</th>
            <th>Submission Status</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <% for (Assignment assignment : assignments) {
            Submission submission = submissionDAO.getSubmissionByStudentAndAssignment(user.getId(), assignment.getId());
        %>
        <tr>
            <td><%= assignment.getTitle() %></td>
            <td><%= assignment.getDeadline() %></td>
            <td>
                <% if (submission != null) { %>
                ✅ Submitted (<a href="<%= submission.getFilePath() %>" target="_blank">View</a>)
                <% } else { %>
                ❌ Not Submitted
                <% } %>
            </td>
            <td>
                <form action="UploadSubmissionServlet" method="post" enctype="multipart/form-data" class="d-inline">
                    <input type="hidden" name="assignmentId" value="<%= assignment.getId() %>">
                    <input type="file" name="file" class="form-control" required>
                    <button type="submit" class="btn btn-success btn-sm mt-1">Upload</button>
                </form>
                <% if (submission != null) { %>
                <a href="DeleteSubmissionServlet?submissionId=<%= submission.getId() %>" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure?')">Delete</a>
                <% } %>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>

    <a href="logout" class="btn btn-danger">Logout</a>
</div>
</body>
</html>
