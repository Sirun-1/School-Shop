/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import model.permission;

public class permissionDAO extends DBContext {

    // Get all permissions for a role
    public Vector<permission> getPermissionsByRole(int roleID) {
        Vector<permission> vector = new Vector<>();
        String sql = "SELECT * FROM [dbo].[permissions] WHERE role_id = ?";
        try {
            PreparedStatement ptm = connection.prepareStatement(sql);
            ptm.setInt(1, roleID);
            ResultSet rs = ptm.executeQuery();
            while (rs.next()) {
                permission p = new permission(
                    rs.getInt(1), rs.getInt(2), rs.getString(3),
                    rs.getBoolean(4), rs.getBoolean(5),
                    rs.getBoolean(6), rs.getBoolean(7)
                );
                vector.add(p);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return vector;
    }

    // Check one specific permission
    public permission getPermission(int roleID, String pageName) {
        String sql = "SELECT * FROM [dbo].[permissions] WHERE role_id = ? AND page_name = ?";
        try {
            PreparedStatement ptm = connection.prepareStatement(sql);
            ptm.setInt(1, roleID);
            ptm.setString(2, pageName);
            ResultSet rs = ptm.executeQuery();
            if (rs.next()) {
                return new permission(
                    rs.getInt(1), rs.getInt(2), rs.getString(3),
                    rs.getBoolean(4), rs.getBoolean(5),
                    rs.getBoolean(6), rs.getBoolean(7)
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    // Insert permissions for a new role (called when adding a new role)
    public void insertPermissions(int roleID, String[] pages,
                                   boolean[] canView, boolean[] canAdd,
                                   boolean[] canEdit, boolean[] canDelete) {
        String sql = "INSERT INTO [dbo].[permissions] " +
                     "(role_id, page_name, can_view, can_add, can_edit, can_delete) " +
                     "VALUES (?,?,?,?,?,?)";
        try {
            for (int i = 0; i < pages.length; i++) {
                PreparedStatement ptm = connection.prepareStatement(sql);
                ptm.setInt(1, roleID);
                ptm.setString(2, pages[i]);
                ptm.setBoolean(3, canView[i]);
                ptm.setBoolean(4, canAdd[i]);
                ptm.setBoolean(5, canEdit[i]);
                ptm.setBoolean(6, canDelete[i]);
                ptm.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Update permissions for a role
    public int updatePermission(permission p) {
        int n = 0;
        String sql = "UPDATE [dbo].[permissions] " +
                     "SET can_view = ?, can_add = ?, can_edit = ?, can_delete = ? " +
                     "WHERE role_id = ? AND page_name = ?";
        try {
            PreparedStatement ptm = connection.prepareStatement(sql);
            ptm.setBoolean(1, p.isCanView());
            ptm.setBoolean(2, p.isCanAdd());
            ptm.setBoolean(3, p.isCanEdit());
            ptm.setBoolean(4, p.isCanDelete());
            ptm.setInt(5, p.getRoleID());
            ptm.setString(6, p.getPageName());
            n = ptm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    // Delete all permissions when a role is deleted
    public int deletePermissionsByRole(int roleID) {
        int n = 0;
        String sql = "DELETE FROM [dbo].[permissions] WHERE role_id = ?";
        try {
            PreparedStatement ptm = connection.prepareStatement(sql);
            ptm.setInt(1, roleID);
            n = ptm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }
}
