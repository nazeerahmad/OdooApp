package com.example.odoomobile.data.purchase;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result {
    @SerializedName("orders_total")
    private Double ordersTotal;
    @SerializedName("orders")
    private List<Order> orders = null;

    public Double getOrdersTotal() {
        return ordersTotal;
    }

    public void setOrdersTotal(Double ordersTotal) {
        this.ordersTotal = ordersTotal;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
