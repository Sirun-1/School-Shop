/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dal.productDAO;
import dal.permissionDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Vector;
import model.bestSellerItem;
import model.permission;
import model.user;

@WebServlet(name = "BestSellerJSP", urlPatterns = {"/bestsellerjsp"})
public class bestSellerJSP extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession(false);
        user sessionUser = (session != null) ? (user) session.getAttribute("user") : null;

        if (sessionUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        permissionDAO pdao = new permissionDAO();
        permission orderPerm = pdao.getPermission(sessionUser.getRoleID(), "order");

        if (orderPerm == null || !orderPerm.isCanView()) {
            request.setAttribute("error", "You do not have permission to view this report.");
            response.sendRedirect("dashboard.jsp");
            return;
        }

        productDAO dao = new productDAO();
        Vector<bestSellerItem> vector = dao.getBestSellers(10); // Top 10 sản phẩm bán chạy nhất

        request.setAttribute("vector", vector);
        request.setAttribute("PageTitle", "Best Sellers");
        request.getRequestDispatcher("jsp/bestSeller.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
