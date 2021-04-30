package com.example.odoomobile.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.odoomobile.R;
import com.example.odoomobile.data.purchase.Order;
import com.example.odoomobile.databinding.PurchaseRowBinding;
import com.example.odoomobile.models.Purchase;
import com.example.odoomobile.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class ViewAllAdapter extends RecyclerView.Adapter<ViewAllAdapter.AllViewHolder> {
    private OrderItemListener listener;
    private List<Order>orderList;

    public ViewAllAdapter(OrderItemListener listener,List<Order>orderList) {
      this.orderList = orderList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AllViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        PurchaseRowBinding purchaseRowBinding = PurchaseRowBinding.inflate(layoutInflater,parent,false);
        return new AllViewHolder(purchaseRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AllViewHolder holder, int position) {
            Order order = orderList.get(position);
            holder.mPurchaseRowBinding.tvPoId.setText("Order Id : "+order.getName());
            holder.mPurchaseRowBinding.tvOrderDate.setText("Order Date : "+order.getDateOrder());
            String partner = order.getPartnerId().get(1) instanceof String ? order.getPartnerId().get(1).toString(): order.getPartnerId().get(0).toString();
            holder.mPurchaseRowBinding.tvVendor.setText("Vendor : "+partner);
            holder.mPurchaseRowBinding.tvTotalPrice.setText("₹"+order.getAmountTotal());
            holder.mPurchaseRowBinding.tvStaus.setText("State :"+order.getState());
            holder.mPurchaseRowBinding.tvConfirm.setOnClickListener(view->listener.onConfirm(order));
            holder.mPurchaseRowBinding.getRoot().setOnClickListener(view -> listener.onItemClick(order));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }


    public class AllViewHolder extends RecyclerView.ViewHolder {
        PurchaseRowBinding mPurchaseRowBinding;
        public AllViewHolder(@NonNull PurchaseRowBinding binding) {
            super(binding.getRoot());
            mPurchaseRowBinding = binding;
        }


    }

    public void updateList(List<Order> orders){
        orderList= new ArrayList<>();
        orderList.addAll(orders);
        notifyDataSetChanged();
    }
    public interface OrderItemListener{
        void onItemClick(Order order);
        void  onConfirm(Order order);
    }
}
