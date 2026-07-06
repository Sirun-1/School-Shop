/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.categoryDAO;
import dal.permissionDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Vector;
import model.category;
import model.permission;
import model.user;

@WebServlet(name = "CategoryJSP", urlPatterns = {"/categoryjsp"})
public class categoryJSP extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession(false);
        user sessionUser = null;
        permission perm = null;
        permissionDAO pdao = new permissionDAO();

        if (session != null && session.getAttribute("user") != null) {
            sessionUser = (user) session.getAttribute("user");
            perm = pdao.getPermission(sessionUser.getRoleID(), "product");
        } else {
            perm = new permission(0, 0, "product", true, false, false, false);
        }

        categoryDAO dao = new categoryDAO();
        request.setAttribute("perm", perm);

        String service = request.getParameter("service");
        if (service == null) {
            service = "ListOfCategory";
        }

        // ── DELETE ──
        if (service.equals("deleteCategory")) {
            if (!perm.isCanDelete()) {
                request.setAttribute("error", "You do not have permission to delete categories.");
                response.sendRedirect("categoryjsp");
                return;
            }
            int cId = Integer.parseInt(request.getParameter("cId"));
            int n = dao.deleteCategory(cId);
            if (n == 0) {
                request.setAttribute("error", "Cannot delete - products still exist in this category.");
                Vector<category> vector = dao.getAllCategory("SELECT * FROM [dbo].[categories]");
                request.setAttribute("vector", vector);
                request.getRequestDispatcher("jsp/category.jsp").forward(request, response);
                return;
            }
            response.sendRedirect("categoryjsp");
            return;
        }

        // ── UPDATE ──
        if (service.equals("updateCategory")) {
            if (!perm.isCanEdit()) {
                request.setAttribute("error", "You do not have permission to edit categories.");
                response.sendRedirect("categoryjsp");
                return;
            }
            String submit = request.getParameter("submit");
            if (submit == null) {
                int cId = Integer.parseInt(request.getParameter("cId"));
                category c = dao.searchCategory(cId);
                request.setAttribute("c", c);
                request.getRequestDispatcher("jsp/updateCategory.jsp").forward(request, response);
            } else {
                int categoryID = Integer.parseInt(request.getParameter("categoryID"));
                String categoryname = request.getParameter("categoryname");
                String description = request.getParameter("description");
                category existing = dao.searchCategory(categoryID);
                String imageUrl = request.getParameter("imageUrl");
                category c = new category(categoryID, existing.getCategoryname(), description, imageUrl);
                dao.updateCategory(c);
                response.sendRedirect("categoryjsp");
            }
            return;
        }

        // ── ADD ──
        if (service.equals("addCategory")) {
            if (!perm.isCanAdd()) {
                request.setAttribute("error", "You do not have permission to add categories.");
                response.sendRedirect("categoryjsp");
                return;
            }
            if (request.getMethod().equalsIgnoreCase("GET")) {
                request.getRequestDispatcher("jsp/insertCategory.jsp").forward(request, response);
                return;
            }
            if (request.getMethod().equalsIgnoreCase("POST")) {
                String categoryname = request.getParameter("categoryname");
                String description = request.getParameter("description");
                String imageUrl = request.getParameter("imageUrl");
                category c = new category(0, categoryname, description, imageUrl);
                dao.insertCategory(c);
                response.sendRedirect("categoryjsp");
                return;
            }
        }

        // ── LIST (default) ──
        if (service.equals("ListOfCategory")) {
            String submit = request.getParameter("submit");
            String categoryname = request.getParameter("categoryname");
            Vector<category> vector;
            if (submit == null) {
                vector = dao.getAllCategory("SELECT * FROM [dbo].[categories] order by category_id asc");
            } else if (categoryname == null || categoryname.trim().isEmpty()) {
        vector = dao.getAllCategory("SELECT * FROM [dbo].[categories] ORDER BY category_id ASC");
            }else{
                vector = dao.getAllCategory("SELECT * FROM [dbo].[categories] WHERE category_name LIKE '%" + categoryname + "%'");
            }
            request.setAttribute("vector", vector);
            request.setAttribute("PageTitle", "Category Management");
            request.getRequestDispatcher("jsp/category.jsp").forward(request, response);
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
