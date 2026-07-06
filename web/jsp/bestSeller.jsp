<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Vector,model.bestSellerItem,model.user"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Best Sellers</title>
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
                <a href="categoryjsp" class="nav-item">
                    <span class="nav-icon"></span>
                    <span class="nav-label">Categories</span>
                </a>
                <% if (u != null) { %><a href="cart?service=showCart" class="nav-item">
                    <span class="nav-icon"></span>
                    <span class="nav-label">My Cart</span>
                </a>
                <% } %>
            </div>
            <div class="sidebar-section">
                <div class="sidebar-label">Management</div>
                <a href="orderjsp" class="nav-item">
                    <span class="nav-icon"></span>
                    <span class="nav-label">Orders</span>
                </a>
                <a href="bestsellerjsp" class="nav-item active">
                    <span class="nav-icon"></span>
                    <span class="nav-label">Best Sellers</span>
                </a>
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
            </div>
            <table>
                <thead>
                    <tr>
                        <th>Rank</th><th>Image</th><th>Product Name</th><th>Total Sold</th><th>Total Revenue</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        Vector<bestSellerItem> vector = (Vector<bestSellerItem>) request.getAttribute("vector");
                        int rank = 1;
                        if (vector != null && !vector.isEmpty()) {
                            for (bestSellerItem b : vector) {
                    %>
                    <tr>
                        <td><%= rank++ %></td>
                        <td>
                            <% if (b.getImage() != null && !b.getImage().isEmpty()) { %>
                            <img src="picture/<%= b.getImage() %>" style="width:50px;height:50px;object-fit:cover;border-radius:4px;">
                            <% } %>
                        </td>
                        <td><%= b.getProductName() %></td>
                        <td><%= b.getTotalSold() %></td>
                        <td><%= String.format("%,.0f", b.getTotalRevenue()) %> VND</td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr><td colspan="5">No sales data yet.</td></tr>
                    <% } %>
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
