<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.app.submission.onlinesubmissionsystem_.model.Assignment, com.app.submission.onlinesubmissionsystem_.dao.AssignmentDAO, com.app.submission.onlinesubmissionsystem_.model.User" %>
<%
  User user = (User) session.getAttribute("user");
  if (user == null || user.getRole() != com.app.submission.onlinesubmissionsystem_.model.Role.INSTRUCTOR) {
    response.sendRedirect("login.jsp");
    return;
  }

  int assignmentId = Integer.parseInt(request.getParameter("assignmentId"));
  AssignmentDAO assignmentDAO = new AssignmentDAO();
  Assignment assignment = assignmentDAO.getAssignmentById((long) assignmentId);

  if (assignment == null) {
    response.sendRedirect("instructorDashboard.jsp");
    return;
  }
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Edit Assignment</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
  <h2>Edit Assignment</h2>
  <form action="UpdateAssignmentServlet" method="post">
    <input type="hidden" name="assignmentId" value="<%= assignment.getId() %>">

    <div class="mb-3">
      <label class="form-label">Title</label>
      <input type="text" name="title" class="form-control" value="<%= assignment.getTitle() %>" required>
    </div>

    <div class="mb-3">
      <label class="form-label">Deadline</label>
      <input type="datetime-local" name="deadline" class="form-control" value="<%= assignment.getDeadline() %>" required>
    </div>

    <button type="submit" class="btn btn-success">Update Assignment</button>
    <a href="instructorDashboard.jsp" class="btn btn-secondary">Cancel</a>
  </form>
</div>
</body>
</html>
