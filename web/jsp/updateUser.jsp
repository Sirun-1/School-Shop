<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Vector,model.user,model.role"%>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Update User</title><link rel="stylesheet" href="style.css"></head>
<body class="auth-page">
<% user u=(user)request.getAttribute("u"); Vector<role> rVector=(Vector<role>)request.getAttribute("rVector"); %>
<div class="card" style="max-width:500px;">
    <div class="brand">E<span>.</span>Store</div>
    <h2>Update User</h2>
    <% String error=(String)session.getAttribute("error"); if(error!=null){ %><div class="error"><%= error %></div><% session.removeAttribute("error"); } %>
    <form action="userjsp" method="POST">
        <input type="hidden" name="service" value="updateUser">
        <input type="hidden" name="submit" value="update">
        <input type="hidden" name="userID" value="<%= u.getUserID() %>">
        <div class="field"><label>Role</label>
            <select name="roleID">
                <% if(rVector!=null){ for(role r:rVector){ %><option value="<%= r.getRoleID() %>" <%= r.getRoleID()==u.getRoleID()?"selected":"" %>><%= r.getRoleName() %></option><% } } %>
            </select>
        </div>
        <div class="field"><label>Full Name</label><input type="text" value="<%= u.getFullname() %>" disabled></div>
        <div class="field"><label>Email *</label><input type="email" name="email" value="<%= u.getEmail() %>" required></div>
        <div class="field"><label>Password *</label><input type="text" name="password" value="<%= u.getPassword() %>" required></div>
        <div class="row">
            <div class="field"><label>Address</label><input type="text" name="address" value="<%= u.getAddress() %>"></div>
            <div class="field"><label>Phone</label><input type="text" name="phone" value="<%= u.getPhone() %>"></div>
        </div>
        <div class="field"><label>Status</label>
            <input type="radio" name="isActivate" value="true" <%= u.isActivate()?"checked":"" %>> Active &nbsp;
            <input type="radio" name="isActivate" value="false" <%= !u.isActivate()?"checked":"" %>> Inactive
        </div>
        <input type="submit" class="btn-primary" value="Update User" style="width:auto;">
        <a href="userjsp">Cancel</a>
    </form>
</div>
</body>
</html>
