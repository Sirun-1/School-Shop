<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.product,model.user,model.permission"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Product Detail - E-Store</title>
        <link rel="stylesheet" href="style.css">
    </head>
    <body>
        <%
            user u = (user) session.getAttribute("user");
            permission perm = (permission) request.getAttribute("perm");
            product p = (product) request.getAttribute("p");
        %>
        <div class="navbar">
            <div class="navbar-left">
                <button class="hamburger" onclick="toggleSidebar()"></button>
                <a href="dashboard.jsp" class="brand">E<span>.</span>Store</a>
            </div>
            <div class="navbar-right">
                <% if (u != null) { %>
                <div class="nav-avatar"><%= u.getFullname().substring(0,1).toUpperCase() %></div>
                <span><%= u.getFullname() %></span>
                <a href="login?service=logout" class="btn-logout">Logout</a>
                <% } else { %>
                <span>Guest</span><a href="login.jsp" class="btn-login">Login</a>
                <% } %>
            </div>
        </div>
        <div class="sidebar" id="sidebar">
            <div class="sidebar-section">
                <div class="sidebar-label">Shop</div>
                <a href="productjsp" class="nav-item"><span class="nav-icon"></span><span class="nav-label">Products</span></a>
                <a href="categoryjsp" class="nav-item"><span class="nav-icon">️</span><span class="nav-label">Categories</span></a>
                <% if (u != null) { %><a href="cart?service=showCart" class="nav-item"><span class="nav-icon"></span><span class="nav-label">My Cart</span></a><% } %>
            </div>
            <div class="sidebar-section">
                <a href="dashboard.jsp" class="nav-item"><span class="nav-icon"></span><span class="nav-label">Dashboard</span></a>
            </div>
        </div>
        <div class="main" id="main">
            <div class="breadcrumb">
                <a href="dashboard.jsp">Home</a><span>›</span>
                <a href="productjsp">Products</a><span>›</span>
                <%= p != null ? p.getProductName() : "Detail" %>
            </div>
            <% if (p == null) { %>
            <p style="color:var(--muted)">Product not found.</p>
            <% } else { %>
            <div class="detail-wrap">
                <div class="detail-img">
                    <% if (p.getImage() != null && !p.getImage().isEmpty()) { %>
                    <img src="picture/<%= p.getImage() %>" alt="<%= p.getProductName() %>">
                    <% } else { %><% } %>
                </div>
                <div class="detail-info">
                    <div class="detail-name"><%= p.getProductName() %></div>
                    <div class="detail-price">$<%= p.getPrice() %></div>
                    <div class="detail-meta">
                        <div class="meta-row"><span class="meta-label">Category ID</span><span class="meta-value"><%= p.getCategoryID() %></span></div>
                        <div class="meta-row">
                            <span class="meta-label">Stock</span>
                            <span class="meta-value">
                                <% if (p.getStock() > 0) { %><span class="stock-ok"><%= p.getStock() %> in stock</span>
                                <% } else { %><span class="stock-out">Out of stock</span><% } %>
                            </span>
                        </div>
                        <div class="meta-row"><span class="meta-label">Added</span><span class="meta-value"><%= p.getCreateDate() %></span></div>
                    </div>
                    <% if (p.getDescription() != null && !p.getDescription().isEmpty()) { %>
                    <div class="detail-desc"><%= p.getDescription() %></div>
                    <% } %>
                    <div class="detail-actions">
                        <% if (u != null) { %><a href="cart?service=addToCart&pId=<%= p.getProductID() %>" class="btn-cart"> Add to Cart</a><% } %>
                        <a href="productjsp" class="btn-back">← Back</a>
                        <% if (perm != null && perm.isCanEdit()) { %><a href="productjsp?service=updateProduct&pId=<%= p.getProductID() %>" class="btn-edit">Edit</a><% } %>
                    </div>
                </div>
            </div>
            <% } %>
        </div>
        <script>function toggleSidebar() {
                document.getElementById('sidebar').classList.toggle('collapsed');
                document.getElementById('main').classList.toggle('expanded');
            }</script>
    </body>
</html>
