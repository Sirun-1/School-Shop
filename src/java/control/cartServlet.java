/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.orderDAO;
import dal.orderItemDAO;
import dal.productDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Vector;
import model.order;
import model.orderItem;
import model.product;
import model.user;

@WebServlet(name = "CartServlet", urlPatterns = {"/cart"})
public class cartServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(true);

        //Session
        user u = (user) session.getAttribute("user");
        if (u == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        orderDAO oDAO = new orderDAO();
        orderItemDAO itemDAO = new orderItemDAO();
        productDAO pDAO = new productDAO();

        String service = request.getParameter("service");
        if (service == null) {
            service = "showCart";
        }

        //Cart
        if (service.equals("showCart")) {
            order pendingOrder = oDAO.getPendingOrderByUser(u.getUserID());
            request.setAttribute("pendingOrder", pendingOrder);
            if (pendingOrder != null) {
                Vector<orderItem> items = itemDAO.getItemsByOrderID(pendingOrder.getOrderID());
                request.setAttribute("items", items);
            }
            request.getRequestDispatcher("jsp/cart.jsp").forward(request, response);
            return;
        }

        //Add
        if (service.equals("addToCart")) {
            int pId = Integer.parseInt(request.getParameter("pId"));
            product p = pDAO.searchProduct(pId);

            order pendingOrder = oDAO.getPendingOrderByUser(u.getUserID());

            if (p.getStock() <= 0) {
                session.setAttribute("error", "Product is out of stock!");
                response.sendRedirect("productjsp");
                return;
            }

            if (pendingOrder == null) {
                order newOrder = new order(0, u.getUserID(), p.getPrice(), "pending", null,
                        u.getAddress() != null ? u.getAddress() : "");
                oDAO.insertOrder(newOrder);
                pendingOrder = oDAO.getPendingOrderByUser(u.getUserID());

                orderItem item = new orderItem(0, pendingOrder.getOrderID(), pId, 1, p.getPrice());
                itemDAO.insertOrderItem(item);

            } else {
                orderItem existing = itemDAO.getItemByOrderAndProduct(pendingOrder.getOrderID(), pId);

                if (existing != null) {
                    if (existing.getQuantity() >= p.getStock()) {
                        session.setAttribute("error", "Cannot add more than available stock!");
                        response.sendRedirect("productjsp");
                        return;
                    }

                    itemDAO.updateQuantity(existing.getItemID(), existing.getQuantity() + 1);

                } else {
                    orderItem item = new orderItem(0, pendingOrder.getOrderID(), pId, 1, p.getPrice());
                    itemDAO.insertOrderItem(item);
                }
                order updated = new order(pendingOrder.getOrderID(),pendingOrder.getUserID(),pendingOrder.getTotal() + p.getPrice(),"pending",null,pendingOrder.getShippingAddress());
                oDAO.updateOrder(updated);
            }

            response.sendRedirect("productjsp");
            return;
        }

        if (service.equals("increaseItem")) {
            int itemID = Integer.parseInt(request.getParameter("itemID"));
            int orderID = Integer.parseInt(request.getParameter("orderID"));
            double price = Double.parseDouble(request.getParameter("price"));

            orderItem item = itemDAO.searchOrderItem(itemID);
            product p = pDAO.searchProduct(item.getProductID());

            if (item.getQuantity() >= p.getStock()) {
                session.setAttribute("error", "Reached maximum stock limit!");
                response.sendRedirect("cart");
                return;
            }

            itemDAO.updateQuantity(itemID, item.getQuantity() + 1);
            order o = oDAO.searchOrder(orderID);
            oDAO.updateOrder(new order(orderID,o.getUserID(),o.getTotal() + price,"pending",null,o.getShippingAddress()));

            response.sendRedirect("cart");
            return;

        }

        if (service.equals("decreaseItem")) {
            int itemID = Integer.parseInt(request.getParameter("itemID"));
            int orderID = Integer.parseInt(request.getParameter("orderID"));
            double price = Double.parseDouble(request.getParameter("price"));
            orderItem item = itemDAO.searchOrderItem(itemID);

            if (item.getQuantity() <= 1) {
                itemDAO.deleteOrderItem(itemID);
                Vector<orderItem> remaining = itemDAO.getItemsByOrderID(orderID);
                if (remaining == null || remaining.isEmpty()) {
                    oDAO.deleteOrder(orderID);
                } else {
                    order o = oDAO.searchOrder(orderID);
                    oDAO.updateOrder(new order(orderID,o.getUserID(),o.getTotal()-price,"pending",null,o.getShippingAddress()));
                }

            } else {
                itemDAO.updateQuantity(itemID, item.getQuantity() - 1);

                order o = oDAO.searchOrder(orderID);
                oDAO.updateOrder(new order(orderID,o.getUserID(),o.getTotal()-price,"pending",null,o.getShippingAddress()));
            }

            response.sendRedirect("cart");
            return;
        }

        //Remove single item
        if (service.equals("removeItem")) {
            int itemID = Integer.parseInt(request.getParameter("itemID"));
            int orderID = Integer.parseInt(request.getParameter("orderID"));
            double price = Double.parseDouble(request.getParameter("price"));

            itemDAO.deleteOrderItem(itemID);
            Vector<orderItem> remaining = itemDAO.getItemsByOrderID(orderID);
            if (remaining == null || remaining.isEmpty()) {
                oDAO.deleteOrder(orderID);
            } else {
                order o = oDAO.searchOrder(orderID);
                oDAO.updateOrder(new order(orderID, o.getUserID(), o.getTotal() - price,"pending", null, o.getShippingAddress()));
            }
            response.sendRedirect("cart");
            return;
        }

        //Delete entire cart 
        if (service.equals("clearCart")) {
            int orderID = Integer.parseInt(request.getParameter("orderID"));
            itemDAO.deleteItemsByOrderID(orderID);
            oDAO.deleteOrder(orderID);
            response.sendRedirect("cart");
            return;
        }

        //Check
        if (service.equals("checkout")) {
            int orderID = Integer.parseInt(request.getParameter("orderID"));
            Vector<orderItem> items = itemDAO.getItemsByOrderID(orderID);
            for (orderItem item : items) {
                product p = pDAO.searchProduct(item.getProductID());
                if (item.getQuantity() > p.getStock()) {
                    session.setAttribute("error",
                            "Not enough stock for product: " + p.getProductName());
                    response.sendRedirect("cart");
                    return;
                }
            }
            for (orderItem item : items) {
                product p = pDAO.searchProduct(item.getProductID());
                pDAO.updateProduct(new product(p.getProductID(),p.getCategoryID(),p.getProductName(),p.getDescription(),p.getPrice(),p.getStock() - item.getQuantity(),p.getImage(),p.getCreateDate()));
            }
            order o = oDAO.searchOrder(orderID);
            oDAO.updateOrder(new order(orderID,o.getUserID(),o.getTotal(),"shipped",null,o.getShippingAddress()));
            session.setAttribute("error", "Order placed successfully!");
            response.sendRedirect("dashboard.jsp");
            return;
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
