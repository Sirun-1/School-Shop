<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Vector,model.permission,model.order,model.user"%>
<!DOCTYPE html>
<html>
    <head><meta charset="UTF-8"><title>Order Management</title><link rel="stylesheet" href="style.css"></head>
    <body>
        <% user u=(user)session.getAttribute("user"); permission perm=(permission)request.getAttribute("perm"); %>
        <div class="navbar">
            <div class="navbar-left">
                <button class="hamburger" onclick="toggleSidebar()">III</button>
                <a href="dashboard.jsp" class="brand">HE<span>200</span>238</a>
            </div>
            <div class="navbar-right">
                <% if(u!=null){ %><div class="nav-avatar"><%= u.getFullname().substring(0,1).toUpperCase() %></div><span><%= u.getFullname() %></span><a href="login?service=logout" class="btn-logout">Logout</a>
                <% }else{ %><span>Guest</span><a href="login.jsp" class="btn-login">Login</a><% } %>
            </div>
        </div>
        <div class="sidebar" id="sidebar">
            <div class="sidebar-section">
                <div class="sidebar-label">Shop</div>
                <a href="productjsp" class="nav-item"><span class="nav-icon"></span><span class="nav-label">Products</span></a>
                <a href="categoryjsp" class="nav-item"><span class="nav-icon">️</span><span class="nav-label">Categories</span></a>
            </div>
            <div class="sidebar-section">
                <div class="sidebar-label">Management</div>
                <a href="orderjsp" class="nav-item active"><span class="nav-icon"></span><span class="nav-label">Orders</span></a>
                <a href="userjsp" class="nav-item"><span class="nav-icon"></span><span class="nav-label">Users</span></a>
                <a href="rolejsp" class="nav-item"><span class="nav-icon"></span><span class="nav-label">Roles</span></a>
            </div>
            <div class="sidebar-section"><a href="dashboard.jsp" class="nav-item"><span class="nav-icon"></span><span class="nav-label">Dashboard</span></a></div>
        </div>
        <div class="main" id="main">
            <% String error=(String)session.getAttribute("error"); if(error!=null){ %><div class="error"><%= error %></div><% session.removeAttribute("error"); } %>
            <div class="topbar">
                <div class="page-title"><%= request.getAttribute("PageTitle") %></div>
                <div class="topbar-right">
                    <form action="orderjsp" method="GET" class="search-form">
                        <input type="hidden" name="service" value="ListOfOrder">
                        <input type="text" name="userID" class="search-input" placeholder="Search by User ID...">
                        <input type="submit" name="submit" class="btn-search" value="Search">
                        <input type="reset" class="btn-search" value="Reset">
                    </form>
                </div>
            </div>
            <table>
                <thead><tr><th>ID</th><th>User ID</th><th>Total</th><th>Status</th><th>Order Date</th><th>Ship Address</th><% if(perm!=null&&(perm.isCanEdit()||perm.isCanDelete())){ %><th>Actions</th><% } %></tr></thead>
                <tbody>
                    <% Vector<order> vector=(Vector<order>)request.getAttribute("vector");
               if(vector!=null){ for(order p:vector){ %>
                    <tr>
                        <td><%= p.getOrderID() %></td>
                        <td><%= p.getUserID() %></td>
                        <td>$<%= p.getTotal() %></td>
                        <td><span class="status-badge status-<%= p.getStatus() %>"><%= p.getStatus() %></span></td>
                        <td><%= p.getOrderDate() %></td>
                        <td><%= p.getShippingAddress() %></td>
                        <% if(perm!=null&&(perm.isCanEdit()||perm.isCanDelete())){ %>
                        <td>
                            <a href="orderjsp?service=viewDetail&oId=<%= p.getOrderID() %>">View</a>
                            <% if(perm.isCanEdit()){ %><a href="orderjsp?service=updateOrder&oId=<%= p.getOrderID() %>">Edit</a><% } %>
                            <% if(perm.isCanDelete()){ %><a href="orderjsp?service=deleteOrder&oId=<%= p.getOrderID() %>" onclick="return confirm('Delete?')">Delete</a><% } %>
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
