<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Register - E-Store</title>
        <link rel="stylesheet" href="style.css">
    </head>
    <body class="auth-page">
        <div class="card">
            <div class="brand">E<span>.</span>Store</div>
            <h2>Create account</h2>
            <p class="subtitle">Join us and start shopping today</p>
            <% String error = (String) session.getAttribute("error"); if (error != null) { %>
            <div class="error"><%= error %></div>
            <% session.removeAttribute("error"); } %>
            <form action="register" method="POST">
                <div class="field">
                    <label>Full Name *</label>
                    <input type="text" name="fullname" placeholder="John Doe" required>
                </div>
                <div class="field">
                    <label>Email *</label>
                    <input type="email" name="email" placeholder="you@example.com" required>
                </div>
                <div class="field">
                    <label>Password *</label>
                    <input type="password" name="password" placeholder="••••••••" required>
                </div>
                <div class="row">
                    <div class="field">
                        <label>Phone</label>
                        <input type="text" name="phone" placeholder="0123456789">
                    </div>
                    <div class="field">
                        <label>Address</label>
                        <input type="text" name="address" placeholder="123 Main St">
                    </div>
                </div>
                <input type="submit" class="btn-primary" value="Create Account" style="width:100%;margin-top:0.5rem;">
            </form>
            <div class="footer-link">Already have an account? <a href="login.jsp">Login</a></div>
        </div>
    </body>
</html>
