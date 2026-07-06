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
import model.order;

/**
 *
 * @author Asus
 */
public class orderDAO extends DBContext {

    public Vector<order> getAllorder(String sql) {
        Vector<order> vector = new Vector<>();
        try {         
            PreparedStatement ptm = connection.prepareStatement(sql);
            ResultSet rs = ptm.executeQuery();
            while(rs.next()){
                order o = new order(rs.getInt(1), rs.getInt(2), rs.getDouble(3), rs.getString(4), rs.getDate(5), rs.getString(6));
                vector.add(o);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return vector;
    }
    public order searchOrder(int orderID) {
    String sql = "SELECT *\n" +
"  FROM [dbo].[orders] where order_id = ?";
    try {
        PreparedStatement ptm = connection.prepareStatement(sql);
        ptm.setInt(1, orderID);
        ResultSet rs = ptm.executeQuery();
        if (rs.next()) {
            return new order(
                    rs.getInt(1),
                    rs.getInt(2),
                    rs.getDouble(3),
                    rs.getString(4),
                    rs.getDate(5),
                    rs.getString(6)
            );
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return null;
}

public int insertOrder(order o) {
    int n = 0;
    String sql = "INSERT INTO [dbo].[orders]\n" +
"           ([user_id]\n" +
"           ,[total_price]\n" +
"           ,[status]\n" +
"           ,[shipping_address])\n" +
"     VALUES\n" +
"           (?,?,?,?)";
    try {
        PreparedStatement ptm = connection.prepareStatement(sql);
        ptm.setInt(1,o.getUserID());
        ptm.setDouble(2, o.getTotal());
        ptm.setString(3,o.getStatus());
        ptm.setString(4, o.getShippingAddress());
        n = ptm.executeUpdate();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return n;
}
public int deleteOrder(int orderID) {
    int n = 0;
    String sql = "DELETE FROM [dbo].[orders]\n" +
"      WHERE order_id = ?";
    try {
        PreparedStatement ptm = connection.prepareStatement(sql);
        ptm.setInt(1, orderID);
        n = ptm.executeUpdate();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return n;
}

public int updateOrder(order o) {
    int n = 0;
    String sql = "UPDATE [dbo].[orders]\n" +
"   SET [total_price] = ?\n" +
"      ,[status] = ?\n" +
"      ,[shipping_address] = ?\n" +
" WHERE order_id = ?";
    try {
        PreparedStatement ptm = connection.prepareStatement(sql);
        ptm.setDouble(1, o.getTotal());
        ptm.setString(2, o.getStatus());
        ptm.setString(3, o.getShippingAddress());
        ptm.setInt(4, o.getOrderID());
        n = ptm.executeUpdate();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return n;
}
public order getLatestOrderByUser(int userID) {
    String sql = "SELECT TOP 1 * FROM [dbo].[orders] WHERE user_id = ? ORDER BY order_date DESC";
    try {
        PreparedStatement ptm = connection.prepareStatement(sql);
        ptm.setInt(1, userID);
        ResultSet rs = ptm.executeQuery();
        if (rs.next()) {
            return new order(
                rs.getInt(1),
                rs.getInt(2),
                rs.getDouble(3),
                rs.getString(4),
                rs.getDate(5),
                rs.getString(6)
            );
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return null;
}
public order getPendingOrderByUser(int userID) {
    String sql = "SELECT * FROM [dbo].[orders] WHERE user_id = ? AND status = 'pending'";
    try {
        PreparedStatement ptm = connection.prepareStatement(sql);
        ptm.setInt(1, userID);
        ResultSet rs = ptm.executeQuery();
        if (rs.next()) {
            return new order(rs.getInt(1), rs.getInt(2), rs.getDouble(3),
                            rs.getString(4), rs.getDate(5), rs.getString(6));
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return null;
}
}
