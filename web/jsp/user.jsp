<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Vector,model.user,model.permission"%>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>User Management</title><link rel="stylesheet" href="style.css"></head>
<body>
<% user u=(user)session.getAttribute("user"); permission perm=(permission)request.getAttribute("perm"); %>
<div class="navbar">
    <div class="navbar-left"><button class="hamburger" onclick="toggleSidebar()">III</button><a href="dashboard.jsp" class="brand">HE<span>200</span>238</a></div>
    <div class="navbar-right">
        <% if(u!=null){ %><div class="nav-avatar"><%= u.getFullname().substring(0,1).toUpperCase() %></div><span><%= u.getFullname() %></span><a href="login?service=logout" class="btn-logout">Logout</a><% }else{ %><span>Guest</span><a href="login.jsp" class="btn-login">Login</a><% } %>
    </div>
</div>
<div class="sidebar" id="sidebar">
    <div class="sidebar-section"><div class="sidebar-label">Management</div>
        <a href="orderjsp" class="nav-item"><span class="nav-icon"></span><span class="nav-label">Orders</span></a>
        <a href="userjsp" class="nav-item active"><span class="nav-icon"></span><span class="nav-label">Users</span></a>
        <a href="rolejsp" class="nav-item"><span class="nav-icon"></span><span class="nav-label">Roles</span></a>
    </div>
    <div class="sidebar-section"><a href="dashboard.jsp" class="nav-item"><span class="nav-icon"></span><span class="nav-label">Dashboard</span></a></div>
</div>
<div class="main" id="main">
    <% String error=(String)session.getAttribute("error"); if(error!=null){ %><div class="error"><%= error %></div><% session.removeAttribute("error"); } %>
    <div class="topbar">
        <div class="page-title"><%= request.getAttribute("PageTitle") %></div>
        <div class="topbar-right">
            <form action="userjsp" method="GET" class="search-form">
                <input type="hidden" name="service" value="ListOfUser">
                <input type="text" name="fullname" class="search-input" placeholder="Search by name...">
                <input type="submit" name="submit" class="btn-search" value="Search">
                <input type="reset" class="btn-search" value="Reset">
            </form>
            <% if(perm!=null&&perm.isCanAdd()){ %><a href="userjsp?service=addUser" class="btn-sm primary">+ Add</a><% } %>
        </div>
    </div>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Role</th>
                <th>Name</th>
                <th>Email</th>
                <th>Address</th>
                <th>Phone</th>
                <th>Registered</th>
                <th>Status</th>
                <% if(perm!=null&&(perm.isCanEdit()||perm.isCanDelete())){ %>
                <th>Actions</th>
                <% } %>
            </tr>
        </thead>
        <tbody>
            <% Vector<user> vector=(Vector<user>)request.getAttribute("vector");
               if(vector!=null){ for(user p:vector){ %>
            <tr>
                <td><%= p.getUserID() %></td>
                <td><%= p.getRoleID() %></td>
                <td><%= p.getFullname() %></td>
                <td><%= p.getEmail() %></td>
                <td><%= p.getAddress() %></td>
                <td><%= p.getPhone() %></td>
                <td><%= p.getregisterDate() %></td>
                <td><span class="status-badge <%= p.isActivate()?"status-delivered":"status-cancelled" %>"><%= p.isActivate()?"Active":"Inactive" %></span></td>
                <% if(perm!=null&&(perm.isCanEdit()||perm.isCanDelete())){ %>
                <td>
                    <% if(perm.isCanEdit()){ %><a href="userjsp?service=updateUser&uId=<%= p.getUserID() %>">Edit</a><% } %>
                    <% if(perm.isCanDelete()){ %><a href="userjsp?service=deleteUser&uId=<%= p.getUserID() %>" onclick="return confirm('Delete?')">Delete</a><% } %>
                </td>
                <% } %>
            </tr>
            <% } } %>
        </tbody>
    </table>
    <a href="dashboard.jsp" class="back-link">← Back to Dashboard</a>
</div>
<script>function toggleSidebar(){document.getElementById('sidebar').classList.toggle('collapsed');document.getElementById('main').classList.toggle('expanded');}</script>
</body>
</html>
