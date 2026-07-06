/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.orderDAO;
import dal.orderItemDAO;
import dal.productDAO;
import dal.userDAO;
import dal.permissionDAO;
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
import model.permission;

@WebServlet(name = "OrderJSP", urlPatterns = {"/orderjsp"})
public class orderJSP extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        //Session and perm check
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        user sessionUser = (user) session.getAttribute("user");
        permissionDAO pdao = new permissionDAO();
        permission perm = pdao.getPermission(sessionUser.getRoleID(), "order");

        if (perm == null || !perm.isCanView()) {
            request.setAttribute("error", "You do not have permission to access this page.");
            request.getRequestDispatcher("unauthorized.jsp").forward(request, response);
            return;
        }

        orderDAO dao = new orderDAO();
        orderItemDAO itemDAO = new orderItemDAO();
        productDAO pDAO = new productDAO();
        userDAO uDAO = new userDAO();
        request.setAttribute("perm", perm); // pass permission to JSP to show/hide buttons

        String service = request.getParameter("service");
        if (service == null) {
            service = "ListOfOrder";
        }
        if (service.equals("addOrder")) {
            if (request.getMethod().equalsIgnoreCase("GET")) {
                request.getRequestDispatcher("jsp/addOrder.jsp").forward(request, response);
                return;
            }
            if (request.getMethod().equalsIgnoreCase("POST")) {
            int userID = Integer.parseInt(request.getParameter("userID"));
            double total = Double.parseDouble(request.getParameter("total"));
            String status = request.getParameter("status");
            String shippingAddress = request.getParameter("shippingAddress");
            order o = new order(0, userID, total, status, null, shippingAddress);
            dao.insertOrder(o);
            response.sendRedirect("orderjsp");
            return;
            }
        }   
        // ── DELETE ──
        if (service.equals("deleteOrder")) {
            if (!perm.isCanDelete()) {
                request.setAttribute("error", "You do not have permission to delete orders.");
                response.sendRedirect("orderjsp");
                return;
            }
            int oId = Integer.parseInt(request.getParameter("oId"));
            itemDAO.deleteItemsByOrderID(oId);
            dao.deleteOrder(oId);
            response.sendRedirect("orderjsp");
            return;
        }

        // ── UPDATE ──
        if (service.equals("updateOrder")) {
            if (!perm.isCanEdit()) {
                request.setAttribute("error", "You do not have permission to edit orders.");
                response.sendRedirect("orderjsp");
                return;
            }
            String submit = request.getParameter("submit");
            if (submit == null) {
                int oId = Integer.parseInt(request.getParameter("oId"));
                order o = dao.searchOrder(oId);
                request.setAttribute("o", o);
                request.getRequestDispatcher("jsp/updateOrder.jsp").forward(request, response);
            } else {
                int orderID = Integer.parseInt(request.getParameter("orderID"));
                double total = Double.parseDouble(request.getParameter("total"));
                String status = request.getParameter("status");
                String shippingAddress = request.getParameter("shippingAddress");
                order o = new order(orderID, 0, total, status, null, shippingAddress);
                dao.updateOrder(o);
                response.sendRedirect("orderjsp");
            }
            return;
        }

        // ── VIEW DETAIL ──
        if (service.equals("viewDetail")) {
            int oId = Integer.parseInt(request.getParameter("oId"));
            order o = dao.searchOrder(oId);
            request.setAttribute("o", o);
            user u = uDAO.searchUser(o.getUserID());
            request.setAttribute("u", u);
            Vector<orderItem> items = itemDAO.getItemsByOrderID(oId);
            request.setAttribute("items", items);
            // Get product names (option 2)
            Vector<product> products = new Vector<>();
            for (orderItem item : items) {
                product p = pDAO.searchProduct(item.getProductID());
                products.add(p);
            }
            request.setAttribute("products", products);
            request.getRequestDispatcher("jsp/orderDetail.jsp").forward(request, response);
            return;
        }

        // ── LIST (default) ──
        if (service.equals("ListOfOrder")) {
            String submit = request.getParameter("submit");
            String userID = request.getParameter("userID");
            Vector<order> vector;
            if (submit == null) {
                vector = dao.getAllorder("SELECT * FROM [dbo].[orders]");
            } else {
                vector = dao.getAllorder("SELECT * FROM [dbo].[orders] WHERE user_id = " + userID);
            }
            request.setAttribute("vector", vector);
            request.setAttribute("PageTitle", "Order Management");
            request.getRequestDispatcher("jsp/order.jsp").forward(request, response);
        }
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
