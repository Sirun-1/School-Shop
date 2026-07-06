/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author phucd
 */
public class bestSellerItem {
    private int productID;
    private String productName;
    private String image;
    private int totalSold;
    private double totalRevenue;

    public bestSellerItem() {
    }

    public bestSellerItem(int productID, String productName, String image, int totalSold, double totalRevenue) {
        this.productID = productID;
        this.productName = productName;
        this.image = image;
        this.totalSold = totalSold;
        this.totalRevenue = totalRevenue;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getTotalSold() {
        return totalSold;
    }

    public void setTotalSold(int totalSold) {
        this.totalSold = totalSold;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}
