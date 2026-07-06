<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Add Category</title>
        <link rel="stylesheet" href="style.css">
    </head>
    <body class="auth-page">
        <div class="card" style="max-width:500px;">
            <div class="brand">E<span>.</span>Store</div>
            <h2>Add New Category</h2>
            <% String error=(String)session.getAttribute("error"); if(error!=null){ %><div class="error"><%= error %></div><% session.removeAttribute("error"); } %>
            <form action="categoryjsp" method="POST">
                <input type="hidden" name="service" value="addCategory">
                <div class="field"><label>Category Name *</label><input type="text" name="categoryname" required></div>
                <div class="field"><label>Description</label><input type="text" name="description"></div>
                <div class="field"><label>Image</label><input type="text" name="imageUrl"></div>
                <input type="submit" class="btn-primary" value="Add Category" style="width:auto;">
                <a href="categoryjsp">Cancel</a>
            </form>
        </div>
    </body>
</html>
