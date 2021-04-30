package com.example.odoomobile.models;

public class Product {
    private String prodId;
    private String prodName;
    private int price;

    public Product(String prodId, String prodName, int price) {
        this.prodId = prodId;
        this.prodName = prodName;
        this.price = price;

    }

    public String getProdId() {
        return prodId;
    }

    public String getProdName() {
        return prodName;
    }

    public int getPrice() {
        return price;
    }

}
