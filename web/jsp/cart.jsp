<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Vector,model.user,model.order,model.orderItem,dal.productDAO,model.product,java.text.DecimalFormat"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Cart - E-Store</title>
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
        <a href="productjsp" class="nav-item"><span class="nav-icon"></span><span class="nav-label">Products</span></a>
        <a href="categoryjsp" class="nav-item"><span class="nav-icon">️</span><span class="nav-label">Categories</span></a>
        <a href="cart?service=showCart" class="nav-item active"><span class="nav-icon"></span><span class="nav-label">My Cart</span></a>
    </div>
    <div class="sidebar-section">
        <a href="dashboard.jsp" class="nav-item"><span class="nav-icon"></span><span class="nav-label">Dashboard</span></a>
    </div>
</div>
<div class="main" id="main">
    <div class="page-title" style="margin-bottom:1.5rem;">My Cart</div>
    <% String error = (String) session.getAttribute("error"); if (error != null) { %><div class="error"><%= error %></div><% session.removeAttribute("error"); } %>
    <%
        DecimalFormat df = new DecimalFormat("#.##");
        order pendingOrder = (order) request.getAttribute("pendingOrder");
        Vector<orderItem> items = (Vector<orderItem>) request.getAttribute("items");
        productDAO pDAO = new productDAO();
    %>
    <% if (pendingOrder == null) { %>
        <div class="empty">Your cart is empty!</div>
        <a href="productjsp" class="btn-sm" style="margin-top:1rem;">Browse Products</a>
    <% } else { %>
        <div class="cart-info">
            <p>Order ID: <strong><%= pendingOrder.getOrderID() %></strong></p>
            <p>Shipping: <%= pendingOrder.getShippingAddress() %></p>
        </div>
        <table>
            <thead>
                <tr>
                    <th>Product</th>
                    <th>Unit Price</th>
                    <th>Quantity</th>
                    <th>Subtotal</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <% double total = 0;
                   if (items != null) { for (orderItem item : items) {
                       product p = pDAO.searchProduct(item.getProductID());
                       double subtotal = item.getPrice() * item.getQuantity();
                       total += subtotal; %>
                <tr>
                    <td><%= p.getProductName() %></td>
                    <td>$<%= df.format(item.getPrice()) %></td>
                    <td>
                        <a href="cart?service=decreaseItem&itemID=<%= item.getItemID() %>&orderID=<%= pendingOrder.getOrderID() %>&price=<%= item.getPrice() %>" class="qty-btn">−</a>
                        <span class="qty-value" ><%= item.getQuantity() %></span>
                        <a href="cart?service=increaseItem&itemID=<%= item.getItemID() %>&orderID=<%= pendingOrder.getOrderID() %>&price=<%= item.getPrice() %>" class="qty-btn">+</a>
                    </td>
                    <td>$<%= df.format(subtotal) %></td>
                    <td><a href="cart?service=removeItem&itemID=<%= item.getItemID() %>&orderID=<%= pendingOrder.getOrderID() %>&price=<%= item.getPrice() * item.getQuantity() %>" onclick="return confirm('Remove?')">Remove</a></td>
                </tr>
                <% } } %>
            </tbody>
        </table>
        <div class="cart-total">Total: $<%= df.format(total) %></div>
        <div class="cart-actions">
            <a href="cart?service=clearCart&orderID=<%= pendingOrder.getOrderID() %>" onclick="return confirm('Clear cart?')" class="btn-clear">Clear Cart</a>
            <a href="productjsp" class="btn-continue">Continue Shopping</a>
            <a href="cart?service=checkout&orderID=<%= pendingOrder.getOrderID() %>" class="btn-checkout">Checkout →</a>
        </div>
    <% } %>
</div>
<script>function toggleSidebar(){document.getElementById('sidebar').classList.toggle('collapsed');document.getElementById('main').classList.toggle('expanded');}</script>
</body>
</html>
