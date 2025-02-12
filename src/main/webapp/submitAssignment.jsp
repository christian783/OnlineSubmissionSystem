<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.app.submission.onlinesubmissionsystem_.model.User" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null || user.getRole() != com.app.submission.onlinesubmissionsystem_.model.Role.STUDENT) {
        response.sendRedirect("login.jsp");
    }

    String assignmentId = request.getParameter("id");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Submit Assignment</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h2>Submit Assignment</h2>
    <form action="uploadSubmission" method="post" enctype="multipart/form-data">
        <input type="hidden" name="assignmentId" value="<%= assignmentId %>">
        <div class="mb-3">
            <label class="form-label">Upload File (PDF, Excel, PPTX, ZIP)</label>
            <input type="file" name="file" class="form-control" required>
        </div>
        <button type="submit" class="btn btn-primary">Submit</button>
        <a href="studentDashboard.jsp" class="btn btn-secondary">Cancel</a>
    </form>
</div>
</body>
</html>
