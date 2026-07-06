<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.order"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Update Order</title>
    <link rel="stylesheet" href="style.css">
</head>
<body class="auth-page">
    <div class="card" style="max-width:500px;">
        <div class="brand">E<span>.</span>Store</div>
        <h2>Update Order</h2>

        <% String error = (String) session.getAttribute("error"); if (error != null) { %>
            <div class="error"><%= error %></div>
        <% session.removeAttribute("error"); } %>

        <% order o = (order) request.getAttribute("o"); %>

        <form action="orderjsp" method="POST">
            <input type="hidden" name="service" value="updateOrder">
            <input type="hidden" name="submit" value="update">
            <input type="hidden" name="orderID" value="<%= o.getOrderID() %>">
            <input type="hidden" name="total" value="<%= o.getTotal() %>">

            <div class="field">
                <label>Order ID</label>
                <input type="text" value="<%= o.getOrderID() %>" disabled>
            </div>
            <div class="field">
                <label>Total Price</label>
                <input type="text" value="$<%= o.getTotal() %>" disabled>
            </div>
            <div class="field">
                <label>Status</label>
                <select name="status" required>
                    <option value="pending"   <%= o.getStatus().equals("pending")   ? "selected" : "" %>>Pending</option>
                    <option value="shipped"   <%= o.getStatus().equals("shipped")   ? "selected" : "" %>>Shipped</option>
                    <option value="delivered" <%= o.getStatus().equals("delivered") ? "selected" : "" %>>Delivered</option>
                    <option value="cancelled" <%= o.getStatus().equals("cancelled") ? "selected" : "" %>>Cancelled</option>
                </select>
            </div>
            <div class="field">
                <label>Shipping Address</label>
                <textarea name="shippingAddress" rows="3" required><%= o.getShippingAddress() %></textarea>
            </div>
            <input type="submit" class="btn-primary" value="Update Order" style="width:auto;">
            <a href="orderjsp">Cancel</a>
        </form>
    </div>
</body>
</html>
