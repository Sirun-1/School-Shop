<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Vector,model.order,model.orderItem,model.product,model.user,java.text.DecimalFormat"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Order Detail</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
<% user u2 = (user) session.getAttribute("user"); %>
<div class="navbar">
    <div class="navbar-left">
        <button class="hamburger" onclick="toggleSidebar()">III</button>
        <a href="dashboard.jsp" class="brand">E<span>.</span>Store</a>
    </div>
    <div class="navbar-right">
        <% if (u2 != null) { %>
            <div class="nav-avatar"><%= u2.getFullname().substring(0,1).toUpperCase() %></div>
            <span><%= u2.getFullname() %></span>
            <a href="login?service=logout" class="btn-logout">Logout</a>
        <% } else { %><span>Guest</span><a href="login.jsp" class="btn-login">Login</a><% } %>
    </div>
</div>
<div class="sidebar" id="sidebar">
    <div class="sidebar-section">
        <div class="sidebar-label">Management</div>
        <a href="orderjsp" class="nav-item active"><span class="nav-icon"></span><span class="nav-label">Orders</span></a>
        <a href="userjsp" class="nav-item"><span class="nav-icon"></span><span class="nav-label">Users</span></a>
        <a href="rolejsp" class="nav-item"><span class="nav-icon"></span><span class="nav-label">Roles</span></a>
    </div>
    <div class="sidebar-section">
        <a href="dashboard.jsp" class="nav-item"><span class="nav-icon"></span><span class="nav-label">Dashboard</span></a>
    </div>
</div>
<div class="main" id="main">
    <div class="page-title" style="margin-bottom:1.5rem;">Order Detail</div>

    <% String error = (String) session.getAttribute("error"); if (error != null) { %>
        <div class="error"><%= error %></div>
    <% session.removeAttribute("error"); } %>

    <%
        DecimalFormat df = new DecimalFormat("#.##");
        order o = (order) request.getAttribute("o");
        user u = (user) request.getAttribute("u");
        Vector<orderItem> items = (Vector<orderItem>) request.getAttribute("items");
        Vector<product> products = (Vector<product>) request.getAttribute("products");
    %>

    <table style="max-width:500px; margin-bottom:2rem;">
        <tr><th>Order ID</th><td><%= o.getOrderID() %></td></tr>
        <tr><th>Customer</th><td><%= u.getFullname() %></td></tr>
        <tr><th>Email</th><td><%= u.getEmail() %></td></tr>
        <tr><th>Status</th><td><span class="status-badge status-<%= o.getStatus() %>"><%= o.getStatus() %></span></td></tr>
        <tr><th>Order Date</th><td><%= o.getOrderDate() %></td></tr>
        <tr><th>Shipping Address</th><td><%= o.getShippingAddress() %></td></tr>
        <tr><th>Total</th><td style="color:var(--accent);font-weight:700;">$<%= df.format(o.getTotal()) %></td></tr>
    </table>

    <div class="section-title">Order Items</div>
    <table>
        <thead>
            <tr><th>Item ID</th><th>Product</th><th>Unit Price</th><th>Qty</th><th>Subtotal</th></tr>
        </thead>
        <tbody>
            <%
                if (items != null && products != null) {
                    for (int i = 0; i < items.size(); i++) {
                        orderItem item = items.get(i);
                        product p = products.get(i);
            %>
            <tr>
                <td><%= item.getItemID() %></td>
                <td><%= p.getProductName() %></td>
                <td>$<%= df.format(item.getPrice()) %></td>
                <td><%= item.getQuantity() %></td>
                <td>$<%= df.format(item.getQuantity() * item.getPrice()) %></td>
            </tr>
            <% } } %>
        </tbody>
    </table>

    <a href="orderjsp" class="back-link">← Back to Orders</a>
</div>
<script>function toggleSidebar(){document.getElementById('sidebar').classList.toggle('collapsed');document.getElementById('main').classList.toggle('expanded');}</script>
</body>
</html>
