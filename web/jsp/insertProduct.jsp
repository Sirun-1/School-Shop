<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Vector,model.category"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Add Product</title>
        <link rel="stylesheet" href="style.css">
    </head>
    <body class="auth-page">
        <div class="card" style="max-width:520px;">
            <div class="brand">E<span>.</span>Store</div>
            <h2>Add New Product</h2>
            <% String error=(String)session.getAttribute("error"); if(error!=null){ %><div class="error"><%= error %></div><% session.removeAttribute("error"); } %>
            <form action="productjsp" method="POST">
                <input type="hidden" name="service" value="addProduct">
                <div class="field"><label>Category *</label>
                    <select name="categoryID" required>
                        <option value="">-- Select Category --</option>
                        <% Vector<category> cVector=(Vector<category>)request.getAttribute("cVector");
                   if(cVector!=null){ for(category c:cVector){ %>
                        <option value="<%= c.getCategoryID() %>"><%= c.getCategoryname() %></option>
                        <% } } %>
                    </select>
                </div>
                <div class="field"><label>Product Name *</label><input type="text" name="productName" required></div>
                <div class="field"><label>Description</label><textarea name="description" rows="3"></textarea></div>
                <div class="row">
                    <div class="field"><label>Price *</label><input type="number" name="price" step="0.01" min="0" required></div>
                    <div class="field"><label>Stock *</label><input type="number" name="stock" min="0" required></div>
                </div>
                <div class="field"><label>Image filename (e.g. mouse.jpg)</label><input type="text" name="image"></div>
                <input type="submit" class="btn-primary" value="Add Product" style="width:auto;">
                <a href="productjsp">Cancel</a>
            </form>
        </div>
    </body>
</html>
