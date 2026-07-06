/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import model.cart;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author phucd
 */
public class cartDAO extends DBContext{
    public cart getCart(int productID) {
    cart cart = null;
    String sql = "SELECT [product_id], [product_name], [price], [stock] " +
                 "FROM [dbo].[products] WHERE product_id = ?";
    try {
        PreparedStatement ptm = connection.prepareStatement(sql);
        ptm.setInt(1, productID);
        ResultSet rs = ptm.executeQuery();
        if (rs.next()) {
            cart = new cart(rs.getInt(1), rs.getString(2), rs.getDouble(3), 0);
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return cart;
}
}
