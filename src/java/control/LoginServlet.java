/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.orderDAO;
import dal.orderItemDAO;
import dal.userDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.Vector;
import model.cart;
import model.order;
import model.orderItem;
import model.user;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String service = request.getParameter("service");
        if (service == null) {
            service = "login";
        }

        //Logout
        if (service.equals("logout")) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                user u = (user) session.getAttribute("user");

                //Save cart
                if (u != null) {
                    // Check if there are any cart items in session
                    Vector<cart> cartItems = new Vector<>();
                    Enumeration enu = session.getAttributeNames();
                    while (enu.hasMoreElements()) {
                        String key = (String) enu.nextElement();
                        Object obj = session.getAttribute(key);
                        if (obj instanceof cart) {
                            cartItems.add((cart) obj);
                        }
                    }

                    //Pending
                    if (!cartItems.isEmpty()) {
                        double total = 0;
                        for (cart c : cartItems) {
                            total += c.getPrice() * c.getQuantity();
                        }

                        orderDAO oDAO = new orderDAO();
                        order o = new order(0, u.getUserID(), total, "pending", null, u.getAddress());
                        oDAO.insertOrder(o);
                        order newOrder = oDAO.getLatestOrderByUser(u.getUserID());
                        orderItemDAO itemDAO = new orderItemDAO();
                        for (cart c : cartItems) {
                            orderItem item = new orderItem(0, newOrder.getOrderID(), c.getProductID(), c.getQuantity(), c.getPrice());
                            itemDAO.insertOrderItem(item);
                        }
                    }
                }
                session.invalidate();
            }
            response.sendRedirect("dashboard.jsp");
            return;
        }

        if (service.equals("login")) {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            if (email == null || email.trim().isEmpty()
                    || password == null || password.trim().isEmpty()) {
                HttpSession session = request.getSession(true);
                session.setAttribute("error", "Please enter email and password!");
                response.sendRedirect("login.jsp");
                return;
            }

            userDAO dao = new userDAO();
            user u = dao.loginUser(email, password);
            if (u == null) {
                HttpSession session = request.getSession(true);
                session.setAttribute("error", "Wrong email or password!");
                response.sendRedirect("login.jsp");
                return;
            }
            if (!u.isActivate()) {
                HttpSession session = request.getSession(true);
                session.setAttribute("error", "Your account has been deactivated!");
                response.sendRedirect("login.jsp");
                return;
            }
            HttpSession session = request.getSession(true);
            session.setAttribute("user", u);
            session.setAttribute("roleID", u.getRoleID());
            session.setAttribute("userID", u.getUserID());

            response.sendRedirect("dashboard.jsp");

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
