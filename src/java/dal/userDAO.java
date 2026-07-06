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
import model.user;

/**
 *
 * @author Asus
 */
public class userDAO extends DBContext {

    public Vector<user> getAllUser(String sql) {
        Vector<user> vector = new Vector<>();
        try {         
            PreparedStatement ptm = connection.prepareStatement(sql);
            ResultSet rs = ptm.executeQuery();
            while(rs.next()){
                user u = new user(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getDate(8),rs.getBoolean(9));
                vector.add(u);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return vector;
    }
    public user searchUser(int userID) {
    String sql = "SELECT *\n" +
"  FROM [dbo].[users] where user_id = ?";
    try {
        PreparedStatement ptm = connection.prepareStatement(sql);
        ptm.setInt(1, userID);
        ResultSet rs = ptm.executeQuery();
        if (rs.next()) {
            return new user(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getDate(8),rs.getBoolean(9));
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return null;
}

public int insertUser(user u) {
    int n = 0;
    String sql = "INSERT INTO [dbo].[users]\n" +
"           ([role_id]\n" +
"           ,[full_name]\n" +
"           ,[email]\n" +
"           ,[password]\n" +
"           ,[address]\n" +
"           ,[phone]\n" +
"           ,[is_active])\n" +
"     VALUES\n" +
"           (?,?,?,?,?,?,?)";
    try {
        PreparedStatement ptm = connection.prepareStatement(sql);
        ptm.setInt(1, u.getRoleID());
        ptm.setString(2, u.getFullname());
        ptm.setString(3, u.getEmail());
        ptm.setString(4, u.getPassword());
        ptm.setString(5, u.getAddress());
        ptm.setString(6, u.getPhone());
        ptm.setBoolean(7,u.isActivate());
        n = ptm.executeUpdate();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return n;
}
public int deleteUser(int userID) {
    int n = 0;
    try {
        // Check current is_active status
        String checkActive = "SELECT is_active FROM [dbo].[users] WHERE user_id = ?";
        PreparedStatement checkPtm = connection.prepareStatement(checkActive);
        checkPtm.setInt(1, userID);
        ResultSet rs = checkPtm.executeQuery();

        if (rs.next()) {
            boolean isActive = rs.getBoolean("is_active");

            if (isActive) {
                // ── FIRST DELETE: user is active → deactivate them ──
                String deactivate = "UPDATE [dbo].[users] SET is_active = 0 WHERE user_id = ?";
                PreparedStatement ptm = connection.prepareStatement(deactivate);
                ptm.setInt(1, userID);
                n = ptm.executeUpdate();
                System.out.println("User deactivated. Delete again to permanently remove.");

            } else {

                ResultSet orderCheck = getData(
                    "SELECT * FROM [dbo].[orders] WHERE user_id = " + userID
                );

                if (orderCheck.next()) {
                    System.err.println("Cannot delete - user has order history.");
                    return n;
                } else {
                    String hardDelete = "DELETE FROM [dbo].[users] WHERE user_id = ?";
                    PreparedStatement ptm = connection.prepareStatement(hardDelete);
                    ptm.setInt(1, userID);
                    n = ptm.executeUpdate();
                    System.out.println("User permanently deleted.");
                }
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return n;
}
   
    public int updateUser(user u) {
    int n = 0;
    String sql = "UPDATE [dbo].[users]\n" +
"   SET [role_id] = ?\n" +
"      ,[full_name] = ?\n" +
"      ,[email] = ?\n" +
"      ,[password] = ?\n" +
"      ,[address] = ?\n" +
"      ,[phone] = ?\n" +
"      ,[is_active] = ?\n" +
" WHERE user_id = ?";
    try {
        PreparedStatement ptm = connection.prepareStatement(sql);
        ptm.setInt(1, u.getRoleID());
        ptm.setString(2, u.getFullname());
        ptm.setString(3, u.getEmail());
        ptm.setString(4, u.getPassword());
        ptm.setString(5, u.getAddress());
        ptm.setString(6, u.getPhone());
        ptm.setBoolean(7,u.isActivate());
        ptm.setInt(8, u.getUserID());
        n = ptm.executeUpdate();
    } catch(SQLException ex){
        ex.printStackTrace();
    }
    return n;
}
    public user loginUser(String email, String password) {
        String sql = "SELECT * FROM [dbo].[users] WHERE email = ? AND password = ?";
        try {
            PreparedStatement ptm = connection.prepareStatement(sql);
            ptm.setString(1, email);
            ptm.setString(2, password);
            ResultSet rs = ptm.executeQuery();
            if (rs.next()) {
                return new user(
                rs.getInt(1),    
                rs.getInt(2),    
                rs.getString(3), 
                rs.getString(4), 
                rs.getString(5), 
                rs.getString(6), 
                rs.getString(7), 
                rs.getDate(8), 
                rs.getBoolean(9)
            );
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return null;
}
    public user searchUserByEmail(String email) {
    String sql = "SELECT * FROM [dbo].[users] WHERE email = ?";
    try {
        PreparedStatement ptm = connection.prepareStatement(sql);
        ptm.setString(1, email);
        ResultSet rs = ptm.executeQuery();
        if (rs.next()) {
            return new user(rs.getInt(1), rs.getInt(2), rs.getString(3),
                           rs.getString(4), rs.getString(5), rs.getString(6),
                           rs.getString(7), rs.getDate(8), rs.getBoolean(9));
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return null;
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
}