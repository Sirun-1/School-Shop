<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Vector,model.product,model.permission,model.user"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Products - E-Store</title>
        <link rel="stylesheet" href="style.css">
    </head>
    <body>
        <%
            user u = (user) session.getAttribute("user");
            permission perm = (permission) request.getAttribute("perm");
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
                <a href="productjsp" class="nav-item active">
                    <span class="nav-icon"></span>
                    <span class="nav-label">Products</span>
                </a>
                <a href="categoryjsp" class="nav-item">
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
            <% String error = (String) session.getAttribute("error"); if (error != null) { %>
            <div class="error"><%=error%>
            </div>
            <% session.removeAttribute("error");}%>
            <div class="topbar">
                <div class="page-title">Products</div>
                <div class="topbar-right">
                    <form action="productjsp" method="GET" class="search-form">
                        <input type="hidden" name="service" value="ListOfProduct">
                        <input type="text" name="productName" class="search-input" placeholder="Search...">
                        <input type="submit" name="submit" class="btn-search" value="Search">
                    </form>
                    <% if (u != null) { %><a href="cart?service=showCart" class="btn-sm"> Cart</a><% } %>
                    <% if (perm != null && perm.isCanAdd()) { %>
                    <a href="productjsp?service=addProduct" class="btn-sm primary">+ Add</a>
                    <% } %>
                </div>
            </div>
            <% Vector<product> vector = (Vector<product>) request.getAttribute("vector"); %>
            <% if (vector == null || vector.isEmpty()) { %>
            <div class="empty">No products found.</div>
            <% } else { %>
            <div class="product-grid">
                <% for (product p : vector) { %>
                <div class="product-card">
                    <a href="productjsp?service=viewDetail&pId=<%= p.getProductID() %>" class="product-img-link">
                        <div class="product-img-wrap">
                            <% if (p.getImage() != null && !p.getImage().isEmpty()) { %>
                            <img src="picture/<%= p.getImage() %>" alt="<%= p.getProductName() %>">
                            <% } else { %><div class="no-img"></div><% } %>
                            <div class="price-badge">$<%= p.getPrice() %></div>
                        </div>
                    </a>
                    <div class="product-info">
                        <div class="product-name"><%= p.getProductName() %></div>
                    </div>
                    <div class="product-actions">
                        <% if (u != null) { %>
                        <a href="cart?service=addToCart&pId=<%= p.getProductID() %>" class="act-btn cart">+ Cart</a><% } %>
                        <% if (perm != null && perm.isCanEdit()) { %>
                        <a href="productjsp?service=updateProduct&pId=<%= p.getProductID() %>" class="act-btn">Edit</a><% } %>
                        <% if (perm != null && perm.isCanDelete()) { %>
                        <a href="productjsp?service=deleteProduct&pId=<%= p.getProductID() %>" onclick="return confirm('Delete?')" class="act-btn del">Delete</a>
                        <% } %>
                    </div>
                </div>
                <% } %>
            </div>
            <% } %>
        </div>
        <script>function toggleSidebar() {
                document.getElementById('sidebar').classList.toggle('collapsed');
                document.getElementById('main').classList.toggle('expanded');
            }</script>
    </body>
</html>
