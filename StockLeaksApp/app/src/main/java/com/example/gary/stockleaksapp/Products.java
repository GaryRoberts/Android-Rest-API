package com.example.gary.stockleaksapp;

public class Products {
    private int id;
    private String productName;
    private String productCategory;
    private String sellType;

    public Products(int id, String productName, String productCategory, String sellType) {
        this.id = id;
        this.productName = productName;
        this.productCategory = productCategory;
        this.sellType = sellType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getSellType() {
        return sellType;
    }

    public void setSellType(String sellType) {
        this.sellType = sellType;
    }
}
