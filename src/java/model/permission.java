/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author phucd
 */
public class permission {
    private int permissionID;
    private int roleID;
    private String pageName;
    private boolean canView;
    private boolean canAdd;
    private boolean canEdit;
    private boolean canDelete;

    public permission() {
    }

    public permission(int permissionID, int roleID, String pageName, boolean canView, boolean canAdd, boolean canEdit, boolean canDelete) {
        this.permissionID = permissionID;
        this.roleID = roleID;
        this.pageName = pageName;
        this.canView = canView;
        this.canAdd = canAdd;
        this.canEdit = canEdit;
        this.canDelete = canDelete;
    }

    public int getPermissionID() {
        return permissionID;
    }

    public void setPermissionID(int permissionID) {
        this.permissionID = permissionID;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public boolean isCanView() {
        return canView;
    }

    public void setCanView(boolean canView) {
        this.canView = canView;
    }

    public boolean isCanAdd() {
        return canAdd;
    }

    public void setCanAdd(boolean canAdd) {
        this.canAdd = canAdd;
    }

    public boolean isCanEdit() {
        return canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }

    public boolean isCanDelete() {
        return canDelete;
    }

    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Permission{");
        sb.append("permissionID=").append(permissionID);
        sb.append("roleID=").append(roleID);       
        sb.append(", pageName=").append(pageName);
        sb.append(", canView=").append(canView);      
        sb.append(", canAdd=").append(canAdd);
        sb.append(", canEdit=").append(canEdit);
        sb.append(", canDelete=").append(canDelete);
        sb.append('}');
        return sb.toString();
    }
}
