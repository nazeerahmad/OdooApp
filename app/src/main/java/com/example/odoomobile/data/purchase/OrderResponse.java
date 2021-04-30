package com.example.odoomobile.data.purchase;

import androidx.recyclerview.widget.DiffUtil;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class OrderResponse {

    private String jsonrpc;
    @SerializedName("id")
    private String id;
    @SerializedName("result")
    private Result result;

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "OrderResponse{" +
                "jsonrpc='" + jsonrpc + '\'' +
                ", id='" + id + '\'' +
                ", result=" + result +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderResponse that = (OrderResponse) o;
        return getJsonrpc().equals(that.getJsonrpc()) &&
                getId().equals(that.getId()) &&
                getResult().equals(that.getResult());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getJsonrpc(), getId(), getResult());
    }

}
