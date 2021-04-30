package com.example.odoomobile.data.purchase;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Order {

    @SerializedName("name")
    private String name;
    @SerializedName("lines")
    private List<Line> lines = null;
    @SerializedName("company_id")
    private List<Object> companyId = null;
    @SerializedName("amount_tax")
    private Double amountTax;
    @SerializedName("state")
    private String state;
    @SerializedName("date_order")
    private String dateOrder;
    @SerializedName("partner_id")
    private List<Object> partnerId = null;
    @SerializedName("id")
    private Integer id;
    @SerializedName("amount_total")
    private Double amountTotal;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Line> getLines() {
        return lines;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }

    public List<Object> getCompanyId() {
        return companyId;
    }

    public void setCompanyId(List<Object> companyId) {
        this.companyId = companyId;
    }

    public Double getAmountTax() {
        return amountTax;
    }

    public void setAmountTax(Double amountTax) {
        this.amountTax = amountTax;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(String dateOrder) {
        this.dateOrder = dateOrder;
    }

    public List<Object> getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(List<Object> partnerId) {
        this.partnerId = partnerId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getAmountTotal() {
        return amountTotal;
    }

    public void setAmountTotal(Double amountTotal) {
        this.amountTotal = amountTotal;
    }



}
