<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Vector,model.product,model.category"%>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Update Product</title><link rel="stylesheet" href="style.css"></head>
<body class="auth-page">
<div class="card" style="max-width:520px;">
    <div class="brand">E<span>.</span>Store</div>
    <h2>Update Product</h2>
    <% String error=(String)session.getAttribute("error"); if(error!=null){ %><div class="error"><%= error %></div><% session.removeAttribute("error"); } %>
    <% product p=(product)request.getAttribute("p"); Vector<category> cVector=(Vector<category>)request.getAttribute("cVector"); %>
    <form action="productjsp" method="POST">
        <input type="hidden" name="service" value="updateProduct">
        <input type="hidden" name="submit" value="update">
        <input type="hidden" name="productID" value="<%= p.getProductID() %>">
        <div class="field"><label>Category *</label>
            <select name="categoryID" required>
                <option value="">-- Select Category --</option>
                <% if(cVector!=null){ for(category c:cVector){ %>
                <option value="<%= c.getCategoryID() %>" <%= c.getCategoryID()==p.getCategoryID()?"selected":"" %>><%= c.getCategoryname() %></option>
                <% } } %>
            </select>
        </div>
        <div class="field"><label>Product Name *</label><input type="text" name="productName" value="<%= p.getProductName() %>" required></div>
        <div class="field"><label>Description</label><textarea name="description" rows="3"><%= p.getDescription() %></textarea></div>
        <div class="row">
            <div class="field"><label>Price *</label><input type="number" name="price" step="0.01" min="0" value="<%= p.getPrice() %>" required></div>
            <div class="field"><label>Stock *</label><input type="number" name="stock" min="0" value="<%= p.getStock() %>" required></div>
        </div>
        <div class="field"><label>Image filename</label><input type="text" name="image" value="<%= p.getImage() %>"></div>
        <input type="submit" class="btn-primary" value="Update Product" style="width:auto;">
        <a href="productjsp">Cancel</a>
    </form>
</div>
</body>
</html>
