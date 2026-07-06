<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.user,model.permission,model.category,dal.permissionDAO,dal.categoryDAO,java.util.Vector"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>E-Store</title>
        <link rel="stylesheet" href="style.css">
    </head>
    <body>
        <%
            user u = (user) session.getAttribute("user");
            permission orderPerm = null;
            permission userPerm  = null;
            permission rolePerm  = null;
            if (u != null) {
                permissionDAO pdao = new permissionDAO();
                orderPerm = pdao.getPermission(u.getRoleID(), "order");
                userPerm  = pdao.getPermission(u.getRoleID(), "user");
                rolePerm  = pdao.getPermission(u.getRoleID(), "role");
            }
            categoryDAO cdao = new categoryDAO();
            Vector<category> categories = cdao.getAllCategory("SELECT * FROM [dbo].[categories] ORDER BY category_id ASC");
        %>
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
                <% } else { %>
                <span>Guest</span>
                <a href="login.jsp" class="btn-login">Login</a>
                <% } %>
            </div>
        </div>
        <div class="sidebar" id="sidebar">
            <div class="sidebar-section">
                <div class="sidebar-label">Shop</div>
                <a href="productjsp" class="nav-item">
                    <span class="nav-icon"></span>
                    <span class="nav-label">Products</span>
                </a>
                <a href="categoryjsp" class="nav-item">
                    <span class="nav-icon">️</span>
                    <span class="nav-label">Categories</span>
                </a>
                <% if (u != null) { %>
                <a href="cart?service=showCart" class="nav-item">
                    <span class="nav-icon"></span>
                    <span class="nav-label">My Cart</span>
                </a>
                <% } %>
            </div>
            <% boolean hasMgmt = (orderPerm != null && orderPerm.isCanView()) || (userPerm != null && userPerm.isCanView()) || (rolePerm != null && rolePerm.isCanView()); %>
            <% if (hasMgmt) { %>
            <div class="sidebar-section">
                <div class="sidebar-label">Management</div>
                <% if (orderPerm != null && orderPerm.isCanView()) { %>
                <a href="orderjsp" class="nav-item">
                    <span class="nav-icon"></span>
                    <span class="nav-label">Orders</span>
                </a>
                <% } %>
                <% if (userPerm  != null && userPerm.isCanView())  { %>
                <a href="userjsp"  class="nav-item">
                    <span class="nav-icon"></span>
                    <span class="nav-label">Users</span>
                </a>
                <% } %>
                <% if (rolePerm  != null && rolePerm.isCanView())  { %>
                <a href="rolejsp"  class="nav-item">
                    <span class="nav-icon"></span>
                    <span class="nav-label">Roles</span>
                </a>
                <% } %>
            </div>
            <% } %>
            <% if (u == null) { %>
            <div class="sidebar-section">
                <div class="sidebar-label">Account</div>
                <a href="login.jsp"    class="nav-item">
                    <span class="nav-icon"></span>
                    <span class="nav-label">Login</span>
                </a>
                <a href="register.jsp" class="nav-item">
                    <span class="nav-icon">️</span>
                    <span class="nav-label">Register</span>
                </a>
            </div>
            <% } %>
        </div>
        <div class="main" id="main">
            <div class="hero">
                <div class="hero-label">Home</div>
                <h1>
                    <% if (u != null) { %>Welcome back, <span><%= u.getFullname().split(" ")[0] %></span><% } else { %>Welcome to <span>E-Store</span><% } %>
                </h1>
            </div>
            <div class="section-title">You may like</div>
            <% if (categories != null && !categories.isEmpty()) { %>
            <div class="category-grid">
                <% for (category c : categories) { %>
                <a href="productjsp?service=ListOfProduct&categoryID=<%= c.getCategoryID() %>" class="category-card">
                    <div class="category-img">
                        <% if (c.getImageUrl() != null && !c.getImageUrl().isEmpty()) { %>
                        <img src="picture/<%= c.getImageUrl() %>" alt="<%= c.getCategoryname() %>">
                        <% } else { %>️
                        <% } %>
                    </div>
                    <span class="category-name"><%= c.getCategoryname() %></span>
                    <% if (c.getDescription() != null && !c.getDescription().isEmpty() && !c.getDescription().startsWith("picture/")) { %>
                    <span class="category-desc"><%= c.getDescription() %></span>
                    <% } %>
                </a>
                <% } %>
            </div>
            <% } else { %><div class="empty">No categories found.</div><% } %>
        </div>
        <script>function toggleSidebar() {
                document.getElementById('sidebar').classList.toggle('collapsed');
                document.getElementById('main').classList.toggle('expanded');
            }</script>
    </body>
</html>
