<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Vector,model.role,model.permission"%>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Update Role</title><link rel="stylesheet" href="style.css"></head>
<body class="auth-page">
<div class="card" style="max-width:560px;">
    <div class="brand">E<span>.</span>Store</div>
    <h2>Update Role</h2>
    <% String error=(String)session.getAttribute("error"); if(error!=null){ %><div class="error"><%= error %></div><% session.removeAttribute("error"); } %>
    <%
        role r=(role)request.getAttribute("r");
        Vector<permission> permissions=(Vector<permission>)request.getAttribute("permissions");
        permission orderPerm=null,productPerm=null,categoryPerm=null,rolePerm=null,userPerm=null;
        if(permissions!=null){ for(permission p:permissions){
            if(p.getPageName().equals("order"))    orderPerm=p;
            if(p.getPageName().equals("product"))  productPerm=p;
            if(p.getPageName().equals("category")) categoryPerm=p;
            if(p.getPageName().equals("role"))     rolePerm=p;
            if(p.getPageName().equals("user"))     userPerm=p;
        }}
    %>
    <form action="rolejsp" method="POST">
        <input type="hidden" name="service" value="updateRole">
        <input type="hidden" name="submit" value="update">
        <input type="hidden" name="roleID" value="<%= r.getRoleID() %>">
        <div class="field"><label>Role Name *</label><input type="text" name="roleName" value="<%= r.getRoleName() %>" required></div>
        <div class="field"><label>Description</label><input type="text" name="description" value="<%= r.getDescription() %>"></div>
        <div class="section-title" style="margin:1.25rem 0 0.75rem;">Permissions</div>
        <table class="perm-table">
            <thead><tr><th>Page</th><th>View</th><th>Add</th><th>Edit</th><th>Delete</th></tr></thead>
            <tbody>
                <tr><td>Order</td>
                    <td><input type="checkbox" name="order_view"     <%= orderPerm!=null&&orderPerm.isCanView()    ?"checked":"" %>></td>
                    <td><input type="checkbox" name="order_add"      <%= orderPerm!=null&&orderPerm.isCanAdd()     ?"checked":"" %>></td>
                    <td><input type="checkbox" name="order_edit"     <%= orderPerm!=null&&orderPerm.isCanEdit()    ?"checked":"" %>></td>
                    <td><input type="checkbox" name="order_delete"   <%= orderPerm!=null&&orderPerm.isCanDelete()  ?"checked":"" %>></td>
                </tr>
                <tr><td>Product</td>
                    <td><input type="checkbox" name="product_view"   <%= productPerm!=null&&productPerm.isCanView()  ?"checked":"" %>></td>
                    <td><input type="checkbox" name="product_add"    <%= productPerm!=null&&productPerm.isCanAdd()   ?"checked":"" %>></td>
                    <td><input type="checkbox" name="product_edit"   <%= productPerm!=null&&productPerm.isCanEdit()  ?"checked":"" %>></td>
                    <td><input type="checkbox" name="product_delete" <%= productPerm!=null&&productPerm.isCanDelete()?"checked":"" %>></td>
                </tr>
                <tr><td>Category</td>
                    <td><input type="checkbox" name="category_view"   <%= categoryPerm!=null&&categoryPerm.isCanView()  ?"checked":"" %>></td>
                    <td><input type="checkbox" name="category_add"    <%= categoryPerm!=null&&categoryPerm.isCanAdd()   ?"checked":"" %>></td>
                    <td><input type="checkbox" name="category_edit"   <%= categoryPerm!=null&&categoryPerm.isCanEdit()  ?"checked":"" %>></td>
                    <td><input type="checkbox" name="category_delete" <%= categoryPerm!=null&&categoryPerm.isCanDelete()?"checked":"" %>></td>
                </tr>
                <tr><td>Role</td>
                    <td><input type="checkbox" name="role_view"     <%= rolePerm!=null&&rolePerm.isCanView()    ?"checked":"" %>></td>
                    <td><input type="checkbox" name="role_add"      <%= rolePerm!=null&&rolePerm.isCanAdd()     ?"checked":"" %>></td>
                    <td><input type="checkbox" name="role_edit"     <%= rolePerm!=null&&rolePerm.isCanEdit()    ?"checked":"" %>></td>
                    <td><input type="checkbox" name="role_delete"   <%= rolePerm!=null&&rolePerm.isCanDelete()  ?"checked":"" %>></td>
                </tr>
                <tr><td>User</td>
                    <td><input type="checkbox" name="user_view"     <%= userPerm!=null&&userPerm.isCanView()    ?"checked":"" %>></td>
                    <td><input type="checkbox" name="user_add"      <%= userPerm!=null&&userPerm.isCanAdd()     ?"checked":"" %>></td>
                    <td><input type="checkbox" name="user_edit"     <%= userPerm!=null&&userPerm.isCanEdit()    ?"checked":"" %>></td>
                    <td><input type="checkbox" name="user_delete"   <%= userPerm!=null&&userPerm.isCanDelete()  ?"checked":"" %>></td>
                </tr>
            </tbody>
        </table>
        <div style="margin-top:1.25rem;">
            <input type="submit" class="btn-primary" value="Update Role" style="width:auto;">
            <a href="rolejsp">Cancel</a>
        </div>
    </form>
</div>
</body>
</html>
