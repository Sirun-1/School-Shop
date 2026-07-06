/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author phucd
 */
public class category {
    private int categoryID;
    private String categoryname;
    private String description;
    private String imageUrl;
    public category() {
    }

    public category(int categoryID, String categoryname, String description, String imageUrl) {
        this.categoryID = categoryID;
        this.categoryname = categoryname;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getImageUrl(){
        return imageUrl; 
    }
    public void setImageUrl(String imageUrl){
        this.imageUrl = imageUrl; 
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Category{");
        sb.append("categoryID=").append(categoryID);
        sb.append(", categoryName=").append(categoryname);
        sb.append(", description=").append(description);
        sb.append(", image=").append(imageUrl);
        sb.append('}');
        return sb.toString();
    }
}
