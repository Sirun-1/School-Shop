<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.category"%>
<!DOCTYPE html>
<html>
    <head><meta charset="UTF-8">
        <title>Update Category</title>
        <link rel="stylesheet" href="style.css"></head>
    <body class="auth-page">
        <div class="card" style="max-width:500px;">
            <div class="brand">E<span>.</span>Store</div>
            <h2>Update Category</h2>
            <% String error=(String)session.getAttribute("error"); if(error!=null){ %><div class="error"><%= error %></div><% session.removeAttribute("error"); } %>
            <% category c=(category)request.getAttribute("c"); %>
            <form action="categoryjsp" method="POST">
                <input type="hidden" name="service" value="updateCategory">
                <input type="hidden" name="submit" value="update">
                <input type="hidden" name="categoryID" value="<%= c.getCategoryID() %>">
                <div class="field"><label>Category Name</label>
                    <input type="text" value="<%= c.getCategoryname() %>" disabled></div>
                <div class="field"><label>Description</label>
                    <input type="text" name="description" value="<%= c.getDescription() %>"></div>
                <div class="field"><label>Image</label>
                    <input type="text" name="imageUrl" value="<%= c.getImageUrl() != null ? c.getImageUrl() : "" %>"></div>
                <input type="submit" class="btn-primary" value="Update" style="width:auto;">
                <a href="categoryjsp">Cancel</a>
            </form>
        </div>
    </body>
</html>
