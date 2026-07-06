package controller;

import dal.categoryDAO;
import dal.productDAO;
import dal.permissionDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Date;
import java.util.Vector;
import model.category;
import model.product;
import model.permission;
import model.user;

@WebServlet(name = "ProductJSP", urlPatterns = {"/productjsp"})
public class productJSP extends HttpServlet {

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

        productDAO dao = new productDAO();
        categoryDAO cdao = new categoryDAO();
        Vector<category> cVector = cdao.getAllCategory("SELECT * FROM [dbo].[categories]");
        request.setAttribute("cVector", cVector);
        request.setAttribute("perm", perm); // pass permission to JSP to show/hide buttons

        String service = request.getParameter("service");
        if (service == null) {
            service = "ListOfProduct";
        }

        //Delete
        if (service.equals("deleteProduct")) {
            if (!perm.isCanDelete()) {
                request.setAttribute("error", "You do not have permission to delete products.");
                response.sendRedirect("productjsp");
                return;
            }
            int pId = Integer.parseInt(request.getParameter("pId"));
            int n = dao.deleteProduct(pId);
            if (n == 0) {
                request.setAttribute("error", "Cannot delete - product exists in order history.");
            }
            response.sendRedirect("productjsp");
            return;
        }

        //Update
        if (service.equals("updateProduct")) {
            if (!perm.isCanEdit()) {
                request.setAttribute("error", "You do not have permission to edit products.");
                response.sendRedirect("productjsp");
                return;
            }
            String submit = request.getParameter("submit");
            if (submit == null) {
                int pId = Integer.parseInt(request.getParameter("pId"));
                product p = dao.searchProduct(pId);
                request.setAttribute("p", p);
                request.getRequestDispatcher("jsp/updateProduct.jsp").forward(request, response);
            } else {
                int productID = Integer.parseInt(request.getParameter("productID"));
                int categoryID = Integer.parseInt(request.getParameter("categoryID"));
                String productName = request.getParameter("productName");
                String description = request.getParameter("description");
                double price = Double.parseDouble(request.getParameter("price"));
                int stock = Integer.parseInt(request.getParameter("stock"));
                String image = request.getParameter("image");
                product p = new product(productID, categoryID, productName, description, price, stock, image, null);
                dao.updateProduct(p);
                response.sendRedirect("productjsp");
            }
            return;
        }

        //Add
        if (service.equals("addProduct")) {
            if (!perm.isCanAdd()) {
                request.setAttribute("error", "You do not have permission to add products.");
                response.sendRedirect("productjsp");
                return;
            }
            if (request.getMethod().equalsIgnoreCase("GET")) {
                request.getRequestDispatcher("jsp/insertProduct.jsp").forward(request, response);
                return;
            }
            if (request.getMethod().equalsIgnoreCase("POST")) {
                int categoryID = Integer.parseInt(request.getParameter("categoryID"));
                String productName = request.getParameter("productName");
                String description = request.getParameter("description");
                double price = Double.parseDouble(request.getParameter("price"));
                int stock = Integer.parseInt(request.getParameter("stock"));
                String image = request.getParameter("image");
                product p = new product(0, categoryID, productName, description, price, stock, image, null);
                dao.insertProduct(p);
                response.sendRedirect("productjsp");
                return;
            }
        }
        if (service.equals("viewDetail")) {
            int pId = Integer.parseInt(request.getParameter("pId"));
            product p = dao.searchProduct(pId);
            request.setAttribute("p", p);
            request.setAttribute("perm", perm); 
            request.getRequestDispatcher("jsp/productDetail.jsp").forward(request, response);
            return;
        }
        //List
        if (service.equals("ListOfProduct")) {
            String submit = request.getParameter("submit");
            String productName = request.getParameter("productName");
            String categoryID = request.getParameter("categoryID");
            Vector<product> vector;

        if (submit == null && (categoryID == null || categoryID.isEmpty())) {
            vector = dao.getAllProduct("SELECT * FROM [dbo].[products] ORDER BY product_id ASC");
                } else if (submit == null && categoryID != null && !categoryID.isEmpty()) {
            vector = dao.getAllProduct("SELECT * FROM [dbo].[products] WHERE category_id = " + categoryID + " ORDER BY product_id ASC");
                } else if (productName == null || productName.trim().isEmpty()) {
            vector = dao.getAllProduct("SELECT * FROM [dbo].[products] ORDER BY product_id ASC");
            } else {
            vector = dao.getAllProduct("SELECT * FROM [dbo].[products] WHERE product_name LIKE '%" + productName + "%' ORDER BY product_id ASC");
                }
        request.setAttribute("vector", vector);
        request.setAttribute("PageTitle", "Product Management");
        request.getRequestDispatcher("jsp/product.jsp").forward(request, response);
        }
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
        return "Product JSP Servlet";
    }
}