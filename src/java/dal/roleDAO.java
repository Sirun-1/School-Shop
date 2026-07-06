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
import model.role;
import java.sql.Date;
/**
 *
 * @author phucd
 */
public class roleDAO extends DBContext {
    public Vector<role> getAllRole(String sql){
        Vector<role> vector = new Vector<>();
        try {
            PreparedStatement ptm = connection.prepareStatement(sql);
            ResultSet rs = ptm.executeQuery();
            while(rs.next()){
                role r = new role(rs.getInt(1), rs.getString(2),rs.getString(3));
                vector.add(r);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return vector;
    }
    public role searchRole(int roleID){
        String sql = "SELECT *\n" +
"  FROM [dbo].[roles] where role_id = ?";
        try {
            PreparedStatement ptm = connection.prepareStatement(sql);
            ptm.setInt(1,roleID);
            ResultSet rs = ptm.executeQuery();
            if(rs.next()){
                role r = new role(rs.getInt(1), rs.getString(2),rs.getString(3));
                return r;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public int insertRole(role r){
        int n = 0;
        String sql = "INSERT INTO [dbo].[roles]\n" +
"           ([role_name]\n" +
"           ,[description])\n" +
"     VALUES\n" +
"           (?,?)";
        try {
            PreparedStatement ptm = connection.prepareStatement(sql);
            ptm.setString(1, r.getRoleName());
            ptm.setString(2, r.getDescription());
            n = ptm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }
    public int updateRole(role r){
        int n =0;
        String sql = "UPDATE [dbo].[roles]\n" +
"   SET [role_name] = ?\n" +
"      ,[description] = ?\n" +
" WHERE role_id = ?";
        try {
            PreparedStatement ptm = connection.prepareStatement(sql);
            ptm.setString(1, r.getRoleName());
            ptm.setString(2,r.getDescription());
            ptm.setInt(3, r.getRoleID());
            n = ptm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }
   public int deleteRole(int roleID) {
    int n = 0;
    try {
        if (roleID == 1 || roleID == 2) {
            System.err.println("Cannot delete.");
            return n;
        }

        ResultSet userCheck = getData(
            "SELECT * FROM [dbo].[users] WHERE role_id = " + roleID
        );

        if (userCheck.next()) {
            System.err.println("Cannot delete.");
            return n;
        }
        String sql = "DELETE FROM [dbo].[roles] WHERE role_id = ?";
        PreparedStatement ptm = connection.prepareStatement(sql);
        ptm.setInt(1, roleID);
        n = ptm.executeUpdate();
        System.out.println("Deleted.");

    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return n;
}
   public role searchRoleByName(String roleName) {
    String sql = "SELECT * FROM [dbo].[roles] WHERE role_name = ?";
    try {
        PreparedStatement ptm = connection.prepareStatement(sql);
        ptm.setString(1, roleName);
        ResultSet rs = ptm.executeQuery();
        if (rs.next()) {
            return new role(rs.getInt(1), rs.getString(2), rs.getString(3));
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