/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author phucd
 */
public class role {
    private int roleID;
    private String roleName;
    private String description;

    public role() {
    }

    public role(int roleID, String roleName, String description) {
        this.roleID = roleID;
        this.roleName = roleName;
        this.description = description;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Role{");
        sb.append("roleID=").append(roleID);
        sb.append(", roleName=").append(roleName);
        sb.append(", description=").append(description);
        sb.append('}');
        return sb.toString();
    }
}
