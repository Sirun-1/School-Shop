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
public class order {
    private int orderID;
    private int userID;
    private double total;
    private String status;
    private Date orderDate;
    private String shippingAddress;

    public order() {
    }

    public order(int orderID, int userID, double total, String status, Date orderDate, String shippingAddress) {
        this.orderID = orderID;
        this.userID = userID;
        this.total = total;
        this.status = status;
        this.orderDate = orderDate;
        this.shippingAddress = shippingAddress;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order{");
        sb.append("orderID=").append(orderID);
        sb.append(", userID=").append(userID);
        sb.append(", total=").append(total);
        sb.append(", status=").append(status);      
        sb.append(", orderDate=").append(orderDate);
        sb.append(", shippingAddress=").append(shippingAddress);
        sb.append('}');
        return sb.toString();
    }
}
