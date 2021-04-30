package com.example.odoomobile.data.purchase;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Line {
    @SerializedName("product_id")
    private List<Object> productId = null;
    @SerializedName("sequence")
    private Integer sequence;
    @SerializedName("order_id")
    private Integer orderId;
    @SerializedName("price_unit")
    private Double priceUnit;
    @SerializedName("price_subtotal")
    private Double priceSubtotal;
    @SerializedName("product_qty")
    private Double productQty;
    @SerializedName("id")
    private Integer id;
    @SerializedName("tax_ids")
    private List<Object> taxIds;

    public List<Object> getProductId() {
        return productId;
    }

    public void setProductId(List<Object> productId) {
        this.productId = productId;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Double getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(Double priceUnit) {
        this.priceUnit = priceUnit;
    }

    public Double getPriceSubtotal() {
        return priceSubtotal;
    }

    public void setPriceSubtotal(Double priceSubtotal) {
        this.priceSubtotal = priceSubtotal;
    }

    public Double getProductQty() {
        return productQty;
    }

    public void setProductQty(Double productQty) {
        this.productQty = productQty;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Object> getTaxIds() {
        return taxIds;
    }

    public void setTaxIds(List<Object> taxIds) {
        this.taxIds = taxIds;
    }
}
