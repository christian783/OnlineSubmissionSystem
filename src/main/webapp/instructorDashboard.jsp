<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List, com.app.submission.onlinesubmissionsystem_.model.Assignment, com.app.submission.onlinesubmissionsystem_.dao.AssignmentDAO, com.app.submission.onlinesubmissionsystem_.model.User" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null || user.getRole() != com.app.submission.onlinesubmissionsystem_.model.Role.INSTRUCTOR) {
        response.sendRedirect("login.jsp");
        return;
    }

    AssignmentDAO assignmentDAO = new AssignmentDAO();
    List<Assignment> assignments = assignmentDAO.getAssignmentsByInstructor(user); // Show only instructor's assignments
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Instructor Dashboard</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h2>Instructor Dashboard</h2>
    <p>Welcome, <strong><%= user.getName() %></strong>!</p>

    <h3 class="mt-4">Your Assignments</h3>
    <a href="createAssignment.jsp" class="btn btn-primary mb-3">Create New Assignment</a>

    <table class="table table-bordered">
        <thead class="table-dark">
        <tr>
            <th>Title</th>
            <th>Deadline</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <% for (Assignment assignment : assignments) { %>
        <tr>
            <td><%= assignment.getTitle() %></td>
            <td><%= assignment.getDeadline() %></td>
            <td>
                <a href="viewSubmissions.jsp?assignmentId=<%= assignment.getId() %>" class="btn btn-info btn-sm">View Submissions</a>
                <a href="editAssignment.jsp?assignmentId=<%= assignment.getId() %>" class="btn btn-warning btn-sm">Edit</a>
                <a href="DeleteAssignmentServlet?assignmentId=<%= assignment.getId() %>" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure?')">Delete</a>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>

    <a href="logout" class="btn btn-danger">Logout</a>
</div>
</body>
</html>
