<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Vector,model.user,model.role"%>
<!DOCTYPE html>
<html>
    <head><meta charset="UTF-8"><title>Add User</title><link rel="stylesheet" href="style.css"></head>
    <body class="auth-page">
        <% Vector<role> rVector=(Vector<role>)request.getAttribute("rVector"); %>
        <div class="card" style="max-width:500px;">
            <div class="brand">E<span>.</span>Store</div>
            <h2>Add New User</h2>
            <% String error=(String)session.getAttribute("error"); if(error!=null){ %><div class="error"><%= error %></div><% session.removeAttribute("error"); } %>
            <form action="userjsp" method="POST">
                <input type="hidden" name="service" value="addUser">
                <div class="field"><label>Role</label>
                    <select name="roleID">
                        <% if(rVector!=null){ for(role r:rVector){ %><option value="<%= r.getRoleID() %>"><%= r.getRoleName() %></option><% } } %>
                    </select>
                </div>
                <div class="field"><label>Full Name *</label><input type="text" name="fullname" required></div>
                <div class="field"><label>Email *</label><input type="email" name="email" required></div>
                <div class="field"><label>Password *</label><input type="text" name="password" required></div>
                <div class="row">
                    <div class="field"><label>Address</label><input type="text" name="address"></div>
                    <div class="field"><label>Phone</label><input type="text" name="phone"></div>
                </div>
                <div class="field"><label>Status</label>
                    <input type="radio" name="activate" value="1" checked> Active &nbsp;
                    <input type="radio" name="activate" value="0"> Inactive
                </div>
                <input type="submit" class="btn-primary" value="Add User" style="width:auto;">
                <a href="userjsp">Cancel</a>
            </form>
        </div>
    </body>
</html>
