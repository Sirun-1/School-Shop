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
public class product {
    private int productID;
    private int categoryID;
    private String productName;
    private String description;
    private double price;
    private int stock;
    private String image;
    private Date createDate;

    public product() {
    }

    public product(int productID, int categoryID, String productName, String description, double price, int stock, String image, Date createDate) {
        this.productID = productID;
        this.categoryID = categoryID;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.image = image;
        this.createDate = createDate;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Product{");
        sb.append("productID=").append(productID);
        sb.append(", productName=").append(productName);
        sb.append(", description=").append(description);
        sb.append(", price=").append(price);      
        sb.append(", stock=").append(stock);
        sb.append(", image=").append(image);
        sb.append(", createDate=").append(createDate);
        sb.append('}');
        return sb.toString();
    }
}
