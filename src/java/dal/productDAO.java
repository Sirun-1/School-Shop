/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;
import dal.DBContext;
import java.util.Vector;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.product;
import java.sql.Date;
import model.category;
import model.bestSellerItem;
/**
 *
 * @author phucd
 */
public class productDAO extends DBContext {
    public Vector<product> getAllProduct(String sql){
        Vector<product> vector = new Vector<>();
        try {
            PreparedStatement ptm = connection.prepareStatement(sql);
            ResultSet rs = ptm.executeQuery();
            while(rs.next()){
                product p = new product(rs.getInt(1), rs.getInt(2),rs.getString(3), rs.getString(4), rs.getDouble(5), rs.getInt(6), rs.getString(7), rs.getDate(8));
                vector.add(p);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return vector;
    }
    public product searchProduct(int productID){
        String sql = "SELECT *\n" +
"  FROM [dbo].[products] where product_id = ?";
        try {
            PreparedStatement ptm = connection.prepareStatement(sql);
            ptm.setInt(1,productID);
            ResultSet rs = ptm.executeQuery();
            if(rs.next()){
                product p = new product(rs.getInt(1), rs.getInt(2),rs.getString(3), rs.getString(4), rs.getDouble(5), rs.getInt(6), rs.getString(7), rs.getDate(8));
                return p;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public int insertProduct(product p){
        int n = 0;
        String sql = "INSERT INTO [dbo].[products]\n" +
"           ([category_id]\n" +
"           ,[product_name]\n" +
"           ,[description]\n" +
"           ,[price]\n" +
"           ,[stock]\n" +
"           ,[image_url])\n" +
"     VALUES\n" +
"           (?,?,?,?,?,?)";
        try {
            PreparedStatement ptm = connection.prepareStatement(sql);
            ptm.setInt(1, p.getCategoryID());
            ptm.setString(2, p.getProductName());
            ptm.setString(3,p.getDescription());
            ptm.setDouble(4,p.getPrice());
            ptm.setInt(5, p.getStock());
            ptm.setString(6, p.getImage());
            n = ptm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(productDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }
    public int updateProduct(product p){
        int n =0;
        String sql = "UPDATE [dbo].[products]\n" +
"   SET [category_id] = ?\n" +
"      ,[product_name] = ?\n" +
"      ,[description] = ?\n" +
"      ,[price] = ?\n" +
"      ,[stock] = ?\n" +
"      ,[image_url] = ?\n" +
" WHERE product_id = ?";
        try {
            PreparedStatement ptm = connection.prepareStatement(sql);
            ptm.setInt(1, p.getCategoryID());
            ptm.setString(2,p.getProductName());
            ptm.setString(3,p.getDescription());
            ptm.setDouble(4, p.getPrice());
            ptm.setInt(5, p.getStock());
            ptm.setString(6, p.getImage());
            ptm.setInt(7, p.getProductID());
            n = ptm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }
   public int deleteProduct(int productID) {
    int n = 0;
    try {
        String checkStock = "SELECT stock FROM [dbo].[products] WHERE product_id = ?";
        PreparedStatement checkPtm = connection.prepareStatement(checkStock);
        checkPtm.setInt(1, productID);
        ResultSet rs = checkPtm.executeQuery();

        if (rs.next()) {
            int currentStock = rs.getInt("stock");

            if (currentStock > 0) {
                String softDelete = "UPDATE [dbo].[products] SET stock = 0 WHERE product_id = ?";
                PreparedStatement ptm = connection.prepareStatement(softDelete);
                ptm.setInt(1, productID);
                n = ptm.executeUpdate();
                System.out.println("Stock set to 0.");

            } else {
                ResultSet orderCheck = getData(
                    "SELECT * FROM [dbo].[order_items] WHERE product_id = " + productID
                );
                if (orderCheck.next()) {
                    System.err.println("Product exists in order history.");
                    return n;
                } else {
                    String hardDelete = "DELETE FROM [dbo].[products] WHERE product_id = ?";
                    PreparedStatement ptm = connection.prepareStatement(hardDelete);
                    ptm.setInt(1, productID);
                    n = ptm.executeUpdate();
                    System.out.println("Product deleted.");
                }
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return n;
}
    public ResultSet getData(String sql){
    ResultSet rs = null;
    try {
        java.sql.Statement state =
            connection.createStatement(
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        rs = state.executeQuery(sql);
        } catch (SQLException ex) {
        ex.printStackTrace();
        }
    return rs;
}
    public Vector<bestSellerItem> getBestSellers(int limit) {
    Vector<bestSellerItem> vector = new Vector<>();
    String sql = "SELECT TOP (?) p.product_id, p.product_name, p.image_url, "
               + "SUM(oi.quantity) AS total_sold, "
               + "SUM(oi.quantity * oi.unit_price) AS total_revenue "
               + "FROM [dbo].[order_items] oi "
               + "JOIN [dbo].[products] p ON oi.product_id = p.product_id "
               + "GROUP BY p.product_id, p.product_name, p.image_url "
               + "ORDER BY total_sold DESC";
    try {
        PreparedStatement ptm = connection.prepareStatement(sql);
        ptm.setInt(1, limit);
        ResultSet rs = ptm.executeQuery();
        while (rs.next()) {
            bestSellerItem b = new bestSellerItem(
                rs.getInt("product_id"),
                rs.getString("product_name"),
                rs.getString("image_url"),
                rs.getInt("total_sold"),
                rs.getDouble("total_revenue")
            );
            vector.add(b);
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return vector;
}
}
