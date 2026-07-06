/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.userDAO;
import dal.roleDAO;
import dal.permissionDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Vector;
import model.user;
import model.role;
import model.permission;

@WebServlet(name = "UserJSP", urlPatterns = {"/userjsp"})
public class userJSP extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        //Session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        user sessionUser = (user) session.getAttribute("user");
        permissionDAO pdao = new permissionDAO();
        permission perm = pdao.getPermission(sessionUser.getRoleID(), "user");

        if (perm == null || !perm.isCanView()) {
            request.setAttribute("error", "You do not have permission to access this page.");
            request.getRequestDispatcher("unauthorized.jsp").forward(request, response);
            return;
        }

        userDAO dao = new userDAO();
        roleDAO rdao = new roleDAO();
        Vector<role> rVector = rdao.getAllRole("SELECT * FROM [dbo].[roles]");
        request.setAttribute("rVector", rVector);
        request.setAttribute("perm", perm); // pass permission to JSP to show/hide buttons

        String service = request.getParameter("service");
        if (service == null) {
            service = "ListOfUser";
        }

        //Delete
        if (service.equals("deleteUser")) {
            if (!perm.isCanDelete()) {
                request.setAttribute("error", "Can't delete.");
                response.sendRedirect("userjsp");
                return;
            }
            int uId = Integer.parseInt(request.getParameter("uId"));
            int n = dao.deleteUser(uId);
            if (n == 0) {
                request.setAttribute("error", "Cannot delete.");
                Vector<user> vector = dao.getAllUser("SELECT * FROM [dbo].[users]");
                request.setAttribute("vector", vector);
                request.getRequestDispatcher("jsp/user.jsp").forward(request, response);
                return;
            }
            response.sendRedirect("userjsp");
            return;
        }

        //Update
        if (service.equals("updateUser")) {
            if (!perm.isCanEdit()) {
                request.setAttribute("error", "You do not have permission to edit users.");
                response.sendRedirect("userjsp");
                return;
            }
            String submit = request.getParameter("submit");
            if (submit == null) {
                int uId = Integer.parseInt(request.getParameter("uId"));
                user u = dao.searchUser(uId);
                request.setAttribute("u", u);
                request.getRequestDispatcher("jsp/updateUser.jsp").forward(request, response);
            } else {
                int userID = Integer.parseInt(request.getParameter("userID"));
                int roleID = Integer.parseInt(request.getParameter("roleID"));
                String email = request.getParameter("email");
                String password = request.getParameter("password");
                String address = request.getParameter("address");
                String phone = request.getParameter("phone");
                user existing = dao.searchUser(userID);
                boolean isActivate = Boolean.parseBoolean(request.getParameter("isActivate"));
                user u = new user(userID, roleID, existing.getFullname(), email, password, address, phone, null, isActivate);
                dao.updateUser(u);
                response.sendRedirect("userjsp");
            }
            return;
        }

        //Add
        if (service.equals("addUser")) {
            if (!perm.isCanAdd()) {
                request.setAttribute("error", "You do not have permission to add users.");
                response.sendRedirect("userjsp");
                return;
            }
            if (request.getMethod().equalsIgnoreCase("GET")) {
                request.getRequestDispatcher("jsp/insertUser.jsp").forward(request, response);
                return;
            }
            if (request.getMethod().equalsIgnoreCase("POST")) {
                int roleID = Integer.parseInt(request.getParameter("roleID"));
                String fullname = request.getParameter("fullname");
                String email = request.getParameter("email");
                String password = request.getParameter("password");
                String address = request.getParameter("address");
                String phone = request.getParameter("phone");
                user u = new user(0, roleID, fullname, email, password, address, phone, null, true);
                dao.insertUser(u);
                response.sendRedirect("userjsp");
                return;
            }
        }

        //List
        if (service.equals("ListOfUser")) {
            String submit = request.getParameter("submit");
            String fullname = request.getParameter("fullname");
            Vector<user> vector;
            if (submit == null) {
                vector = dao.getAllUser("SELECT * FROM [dbo].[users]");
            } else {
                vector = dao.getAllUser("SELECT * FROM [dbo].[users] WHERE full_name LIKE '%" + fullname + "%'");
            }
            request.setAttribute("vector", vector);
            request.setAttribute("PageTitle", "User Management");
            request.getRequestDispatcher("jsp/user.jsp").forward(request, response);
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
