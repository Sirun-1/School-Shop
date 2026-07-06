<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Login - E-Store</title>
        <link rel="stylesheet" href="style.css">
    </head>
    <body class="auth-page">
        <div class="card">
            <div class="brand">E<span>.</span>Store</div>
            <h2>Welcome back</h2>
            <p class="subtitle">Sign in to your account</p>
            <% String error = (String) session.getAttribute("error"); if (error != null) { %>
            <div class="error"><%= error %></div>
            <% session.removeAttribute("error"); } %>
            <form action="login" method="POST">
                <div class="field">
                    <label>Email</label>
                    <input type="email" name="email" placeholder="you@example.com" required>
                </div>
                <div class="field">
                    <label>Password</label>
                    <input type="password" name="password" placeholder="••••••••" required>
                </div>
                <input type="submit" class="btn-primary" value="Login" style="width:100%;margin-top:0.5rem;">
                <input type="reset" class="btn-reset" value="Reset" style="width:100%;margin-top:0.6rem;">
            </form>
            <div class="footer-link">Don't have an account? <a href="register.jsp">Register</a></div>
        </div>
    </body>
</html>
