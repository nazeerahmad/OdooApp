package com.example.odoomobile.models;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.util.List;
import java.util.Objects;

public class Purchase {
    private int sno;
    private String pId;
    private String orderDate;
    private String vendorName;
    private int quantity;
    private int totalAmount;
    private List<Product>products;

    public Purchase(int sno,String pId, String orderDate, String vendorName, int quantity, int totalAmount, List<Product> products) {
        this.sno= sno;
        this.pId = pId;
        this.orderDate = orderDate;
        this.vendorName = vendorName;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
        this.products = products;
    }

    public String getId() {
        return pId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getVendorName() {
        return vendorName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public List<Product> getProducts() {
        return products;
    }

    public int getSno() {
        return sno;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "sno=" + sno +
                ", pId='" + pId + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", vendorName='" + vendorName + '\'' +
                ", quantity=" + quantity +
                ", totalAmount=" + totalAmount +
                ", products=" + products +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Purchase purchase = (Purchase) o;
        return getSno() == purchase.getSno() &&
                getQuantity() == purchase.getQuantity() &&
                getTotalAmount() == purchase.getTotalAmount() &&
                pId.equals(purchase.pId) &&
                getOrderDate().equals(purchase.getOrderDate()) &&
                getVendorName().equals(purchase.getVendorName()) &&
                getProducts().equals(purchase.getProducts());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSno(), pId, getOrderDate(), getVendorName(), getQuantity(), getTotalAmount(), getProducts());
    }

    public static DiffUtil.ItemCallback<Purchase> itemCallback = new DiffUtil.ItemCallback<Purchase>() {
        @Override
        public boolean areItemsTheSame(@NonNull Purchase oldItem, @NonNull Purchase newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Purchase oldItem, @NonNull Purchase newItem) {
            return oldItem.equals(newItem);
        }
    };



}
