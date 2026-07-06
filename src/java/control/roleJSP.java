/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

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
import model.role;
import model.permission;
import model.user;

@WebServlet(name = "RoleJSP", urlPatterns = {"/rolejsp"})
public class roleJSP extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // ── Session & Permission Check ──
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        user sessionUser = (user) session.getAttribute("user");
        permissionDAO pdao = new permissionDAO();
        permission perm = pdao.getPermission(sessionUser.getRoleID(), "role");

        if (perm == null || !perm.isCanView()) {
            request.setAttribute("error", "You do not have permission to access this page.");
            request.getRequestDispatcher("unauthorized.jsp").forward(request, response);
            return;
        }

        roleDAO dao = new roleDAO();
        request.setAttribute("perm", perm); // pass permission to JSP to show/hide buttons

        String service = request.getParameter("service");
        if (service == null) {
            service = "ListOfRole";
        }

        // ── DELETE ──
        if (service.equals("deleteRole")) {
            if (!perm.isCanDelete()) {
                request.setAttribute("error", "You do not have permission to delete roles.");
                response.sendRedirect("rolejsp");
                return;
            }
            int rId = Integer.parseInt(request.getParameter("rId"));
            // Delete permissions first before deleting role
            pdao.deletePermissionsByRole(rId);
            int n = dao.deleteRole(rId);
            if (n == 0) {
                request.setAttribute("error", "Cannot delete - protected role or users still assigned.");
                Vector<role> vector = dao.getAllRole("SELECT * FROM [dbo].[roles]");
                request.setAttribute("vector", vector);
                request.getRequestDispatcher("jsp/role.jsp").forward(request, response);
                return;
            }
            response.sendRedirect("rolejsp");
            return;
        }

        // ── UPDATE ──
        if (service.equals("updateRole")) {
            if (!perm.isCanEdit()) {
                session.setAttribute("error", "You do not have permission to edit roles.");
                response.sendRedirect("rolejsp");
                return;
            }
            String submit = request.getParameter("submit");
            if (submit == null) {
                // Show update form with current permissions
                int rId = Integer.parseInt(request.getParameter("rId"));
                role r = dao.searchRole(rId);
                request.setAttribute("r", r);
                Vector<permission> permissions = pdao.getPermissionsByRole(rId);
                request.setAttribute("permissions", permissions);
                request.getRequestDispatcher("jsp/updateRole.jsp").forward(request, response);
            } else {
                // Block editing admin and customer
                int roleID = Integer.parseInt(request.getParameter("roleID"));
                if (roleID == 1 || roleID == 2) {
                    session.setAttribute("error", "Cannot edit admin or customer role permissions.");
                    response.sendRedirect("rolejsp");
                    return;
                }
                // Save updated role
                String roleName = request.getParameter("roleName");
                String description = request.getParameter("description");
                role r = new role(roleID, roleName, description);
                dao.updateRole(r);
                // Update permissions
                String[] pages = {"order", "product", "category", "role", "user"};
                for (String page : pages) {
                    boolean canView   = request.getParameter(page + "_view")   != null;
                    boolean canAdd    = request.getParameter(page + "_add")    != null;
                    boolean canEdit   = request.getParameter(page + "_edit")   != null;
                    boolean canDelete = request.getParameter(page + "_delete") != null;
                    permission p = new permission(0, roleID, page, canView, canAdd, canEdit, canDelete);
                    pdao.updatePermission(p);
                }
                response.sendRedirect("rolejsp");
            }
            return;
        }

        // ── ADD ──
        if (service.equals("addRole")) {
            if (!perm.isCanAdd()) {
                request.setAttribute("error", "You do not have permission to add roles.");
                response.sendRedirect("rolejsp");
                return;
            }
            if (request.getMethod().equalsIgnoreCase("GET")) {
                request.getRequestDispatcher("jsp/insertRole.jsp").forward(request, response);
                return;
            }
            if (request.getMethod().equalsIgnoreCase("POST")) {
                // Save new role
                String roleName = request.getParameter("roleName");
                String description = request.getParameter("description");
                role r = new role(0, roleName, description);
                dao.insertRole(r);
                // Get new role's ID
                role newRole = dao.searchRoleByName(roleName);
                // Save permissions from checkboxes
                String[] pages = {"order", "product", "category", "role", "user"};
                boolean[] canView   = new boolean[pages.length];
                boolean[] canAdd    = new boolean[pages.length];
                boolean[] canEdit   = new boolean[pages.length];
                boolean[] canDelete = new boolean[pages.length];
                for (int i = 0; i < pages.length; i++) {
                    canView[i]   = request.getParameter(pages[i] + "_view")   != null;
                    canAdd[i]    = request.getParameter(pages[i] + "_add")    != null;
                    canEdit[i]   = request.getParameter(pages[i] + "_edit")   != null;
                    canDelete[i] = request.getParameter(pages[i] + "_delete") != null;
                }
                pdao.insertPermissions(newRole.getRoleID(), pages, canView, canAdd, canEdit, canDelete);
                response.sendRedirect("rolejsp");
                return;
            }
        }

        // ── LIST (default) ──
        if (service.equals("ListOfRole")) {
            String submit = request.getParameter("submit");
            String roleName = request.getParameter("roleName");
            Vector<role> vector;
            if (submit == null) {
                vector = dao.getAllRole("SELECT * FROM [dbo].[roles]");
            } else {
                vector = dao.getAllRole("SELECT * FROM [dbo].[roles] WHERE role_name LIKE '%" + roleName + "%'");
            }
            request.setAttribute("vector", vector);
            request.setAttribute("PageTitle", "Role Management");
            request.getRequestDispatcher("jsp/role.jsp").forward(request, response);
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
