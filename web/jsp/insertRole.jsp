<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Add Role</title>
        <link rel="stylesheet" href="style.css"></head>
    <body class="auth-page">
        <div class="card" style="max-width:560px;">
            <div class="brand">E<span>.</span>Store</div>
            <h2>Add New Role</h2>
            <% String error=(String)session.getAttribute("error"); if(error!=null){ %>
            <div class="error"><%= error %></div>
            <% session.removeAttribute("error"); } %>
            <form action="rolejsp" method="POST">
                <input type="hidden" name="service" value="addRole">
                <div class="field"><label>Role Name *</label><input type="text" name="roleName" required></div>
                <div class="field"><label>Description</label><textarea name="description" rows="3"></textarea></div>
                <div class="section-title" style="margin:1.25rem 0 0.75rem;">Permissions</div>
                <table class="perm-table">
                    <thead>
                        <tr>
                            <th>Page</th>
                            <th>View</th>
                            <th>Add</th>
                            <th>Edit</th>
                            <th>Delete</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>Order</td>
                            <td><input type="checkbox" name="order_view"></td>
                            <td><input type="checkbox" name="order_add"></td>
                            <td><input type="checkbox" name="order_edit"></td>
                            <td><input type="checkbox" name="order_delete"></td>
                        </tr>
                        <tr>
                            <td>Product</td>
                            <td><input type="checkbox" name="product_view"></td>
                            <td><input type="checkbox" name="product_add"></td>
                            <td><input type="checkbox" name="product_edit"></td>
                            <td><input type="checkbox" name="product_delete"></td>
                        </tr>
                        <tr>
                            <td>Category</td><td><input type="checkbox" name="category_view"></td>
                            <td><input type="checkbox" name="category_add"></td>
                            <td><input type="checkbox" name="category_edit"></td>
                            <td><input type="checkbox" name="category_delete"></td>
                        </tr>
                        <tr>
                            <td>Role</td>
                            <td><input type="checkbox" name="role_view"></td>
                            <td><input type="checkbox" name="role_add"></td>
                            <td><input type="checkbox" name="role_edit"></td>
                            <td><input type="checkbox" name="role_delete"></td>
                        </tr>
                        <tr>
                            <td>User</td>
                            <td><input type="checkbox" name="user_view"></td>
                            <td><input type="checkbox" name="user_add"></td>
                            <td><input type="checkbox" name="user_edit"></td><td>
                                <input type="checkbox" name="user_delete"></td>
                        </tr>
                    </tbody>
                </table>
                <div style="margin-top:1.25rem;">
                    <input type="submit" class="btn-primary" value="Add Role" style="width:auto;">
                    <a href="rolejsp">Cancel</a>
                </div>
            </form>
        </div>
    </body>
</html>
