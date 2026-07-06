<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Vector,model.category,model.permission,model.user"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Category Management</title>
        <link rel="stylesheet" href="style.css">
    </head>
    <body>
        <% user u = (user) session.getAttribute("user"); %>
        <div class="navbar">
            <div class="navbar-left">
                <button class="hamburger" onclick="toggleSidebar()">III</button>
                <a href="dashboard.jsp" class="brand">HE<span>200</span>238</a>
            </div>
            <div class="navbar-right">
                <% if (u != null) { %>
                <div class="nav-avatar"><%= u.getFullname().substring(0,1).toUpperCase() %></div>
                <span><%= u.getFullname() %></span>
                <a href="login?service=logout" class="btn-logout">Logout</a>
                <% } else { %><span>Guest</span><a href="login.jsp" class="btn-login">Login</a><% } %>
            </div>
        </div>
        <div class="sidebar" id="sidebar">
            <div class="sidebar-section">
                <div class="sidebar-label">Shop</div>
                <a href="productjsp" class="nav-item">
                    <span class="nav-icon"></span>
                    <span class="nav-label">Products</span>
                </a>
                <a href="categoryjsp" class="nav-item active">
                    <span class="nav-icon">️</span>
                    <span class="nav-label">Categories</span>
                </a>
                <% if (u != null) { %><a href="cart?service=showCart" class="nav-item">
                    <span class="nav-icon"></span>
                    <span class="nav-label">My Cart</span>
                </a>
                <% } %>
            </div>
            <div class="sidebar-section">
                <a href="dashboard.jsp" class="nav-item">
                    <span class="nav-icon"></span>
                    <span class="nav-label">Dashboard</span>
                </a>
            </div>
        </div>
        <div class="main" id="main">
            <% String error = (String) session.getAttribute("error"); if (error != null) { %><div class="error"><%= error %></div><% session.removeAttribute("error"); } %>
            <div class="topbar">
                <div class="page-title"><%= request.getAttribute("PageTitle") %></div>
                <div class="topbar-right">
                    <form action="categoryjsp" method="GET" class="search-form">
                        <input type="hidden" name="service" value="ListOfCategory">
                        <input type="text" name="categoryname" class="search-input" placeholder="Search by name...">
                        <input type="submit" name="submit" class="btn-search" value="Search">
                        <input type="reset" class="btn-search" value="Reset">
                    </form>
                    <% permission perm = (permission) request.getAttribute("perm"); %>
                    <% if (perm != null && perm.isCanAdd()) { %><a href="categoryjsp?service=addCategory" class="btn-sm primary">+ Add</a><% } %>
                </div>
            </div>
            <table>
                <thead>
                    <tr>
                        <th>ID</th><th>Category Name</th><th>Description</th>
                        <% if (perm != null && (perm.isCanEdit() || perm.isCanDelete())) { %><th>Actions</th><% } %>
                    </tr>
                </thead>
                <tbody>
                    <% Vector<category> vector = (Vector<category>) request.getAttribute("vector");
               if (vector != null) { for (category p : vector) { %>
                    <tr>
                        <td><%= p.getCategoryID() %></td>
                        <td><%= p.getCategoryname() %></td>
                        <td><%= p.getDescription() %></td>
                        <% if (perm != null && (perm.isCanEdit() || perm.isCanDelete())) { %>
                        <td>
                            <% if (perm.isCanEdit()) { %><a href="categoryjsp?service=updateCategory&cId=<%= p.getCategoryID() %>">Edit</a><% } %>
                            <% if (perm.isCanDelete()) { %><a href="categoryjsp?service=deleteCategory&cId=<%= p.getCategoryID() %>" onclick="return confirm('Delete?')">Delete</a><% } %>
                        </td>
                        <% } %>
                    </tr>
                    <% } } %>
                </tbody>
            </table>
            <a href="dashboard.jsp" class="back-link">← Back to Dashboard</a>
        </div>
        <script>function toggleSidebar() {
                document.getElementById('sidebar').classList.toggle('collapsed');
                document.getElementById('main').classList.toggle('expanded');
            }</script>
    </body>
</html>
