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
import model.category;
/**
 *
 * @author phucd
 */
public class categoryDAO extends DBContext {
    public Vector<category> getAllCategory(String sql){
        Vector<category> vector = new Vector<>();
        try {
            PreparedStatement ptm = connection.prepareStatement(sql);
            ResultSet rs = ptm.executeQuery();
            while (rs.next()){
                category c = new category(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
                vector.add(c);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return vector;
    }
    public category searchCategory(int categoryID) {
    String sql = "SELECT * FROM [dbo].[categories] WHERE category_id = ?";
        try {
            PreparedStatement ptm = connection.prepareStatement(sql);
            ptm.setInt(1, categoryID);
            ResultSet rs = ptm.executeQuery();
            if (rs.next()) {
                return new category(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)); // ← add rs.getString(4)
            }
        } catch (SQLException ex) {
        ex.printStackTrace();
        }
        return null;
    }
    public int insertCategory(category c){
        int n = 0;
        String sql = "INSERT INTO categories (category_name, description, image_url) VALUES (?,?,?)";
        try {
            PreparedStatement ptm = connection.prepareStatement(sql);
            ptm.setString(1,c.getCategoryname());
            ptm.setString(2, c.getDescription());
            ptm.setString(3, c.getImageUrl());
            n = ptm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }
    public int updateCategory(category c){
        int n = 0;
        String sql = "UPDATE categories SET description=?, image_url=? WHERE category_id=?";
        try {
            PreparedStatement ptm = connection.prepareStatement(sql);
            ptm.setString(1, c.getDescription());
            ptm.setString(2, c.getImageUrl());
            ptm.setInt(3, c.getCategoryID());
            n = ptm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }
    public ResultSet getData(String sql){
    ResultSet rs = null;
    try {
        java.sql.Statement state = connection.createStatement(
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        rs = state.executeQuery(sql);
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return rs;
}
    public int deleteCategory(int categoryID){
        int n = 0;
        String sql = "DELETE FROM [dbo].[categories]\n" +
"      WHERE category_id = ?";
        try {
            PreparedStatement ptm = connection.prepareStatement(sql);
            ResultSet rs = getData("SELECT * FROM products WHERE category_id = " + categoryID);
            if(rs.next()){
                System.err.println("There are still product of this category");
                return n;
            }
            ptm.setInt(1, categoryID);
            n = ptm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }
}
