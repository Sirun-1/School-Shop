/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

/**
 *
 * @author phucd
 */
public class user {
    private int userID;
    private int roleID;
    private String fullname;
    private String email;
    private String password;
    private String address;
    private String phone;
    private Date registerDate;
    private boolean isActivate;

    public user() {
    }

    public user(int userID, int roleID, String fullname, String email, String password,String address, String phone, Date registerDate,boolean isActivate) {
        this.userID = userID;
        this.roleID = roleID;
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;       
        this.registerDate = registerDate;
        this.isActivate = isActivate;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
    
    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setAddress(String address){
        this.address = address;
    }
    
    public String getAddress(){
        return address;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getregisterDate() {
        return registerDate;
    }

    public void setregisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }
    public boolean isActivate() {
    return isActivate;
}
public void setActive(boolean isActivate) {
    this.isActivate = isActivate;
}
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Users{");
        sb.append("userID=").append(userID);
        sb.append(", roleID=").append(roleID);
        sb.append(", fullName=").append(fullname);
        sb.append(", email=").append(email);
        sb.append(", password=").append(password);      
        sb.append(", address=").append(address);
        sb.append(", phone=").append(phone);
        sb.append(", registerDate=").append(registerDate);
        sb.append('}');
        return sb.toString();
    }
}
