package com.example.odoomobile;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.odoomobile.application.VolleyCallback;
import com.example.odoomobile.data.purchase.Line;
import com.example.odoomobile.data.purchase.Order;
import com.example.odoomobile.data.purchase.OrderResponse;
import com.example.odoomobile.databinding.FragmentDetailBinding;
import com.example.odoomobile.models.Product;
import com.example.odoomobile.models.Purchase;
import com.example.odoomobile.sharedPref.MyPreferences;
import com.example.odoomobile.sharedPref.PrefConf;
import com.example.odoomobile.utils.HelperClass;
import com.example.odoomobile.viewmodels.PurchaseViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import oogbox.api.odoo.OdooUser;


public class DetailFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private FragmentDetailBinding mFragmentDetailBinding;
    private String[] tHeaders= {"Order Id","Product Name","Price","Qty","Sub Total"};
    private PurchaseViewModel mViewModel;
    private Context mContext;
    private Order order;
    public DetailFragment() {
        // Required empty public constructor
    }


    public static DetailFragment newInstance(String param1, String param2) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onAttach(@NonNull Context context) {

        super.onAttach(context);

        if(context instanceof MainActivity){
            mContext = context;
        }
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentDetailBinding = FragmentDetailBinding.inflate(inflater,container,false);
        return mFragmentDetailBinding.getRoot();
    }





    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();
        mFragmentDetailBinding.tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmOrder();
                Snackbar.make(requireView(),"Something went wrong!!",Snackbar.LENGTH_LONG).show();

            }
        });

    }

    public void init() {
        Gson gson = new Gson();
        Type type = new TypeToken<Order>() {
        }.getType();
        double total=0.0;
        order =  gson.fromJson(MyPreferences.getInstance(mContext).getString(PrefConf.PURCHASE, ""), type);
        TableLayout.LayoutParams layoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0,0,2,0);
        TableRow tableRowHeader = new TableRow(mContext);
        String partner = order.getPartnerId().get(1) instanceof String ? order.getPartnerId().get(1).toString(): order.getPartnerId().get(0).toString();

        mFragmentDetailBinding.tvOrderDate.setText("Order Date : "+order.getDateOrder());
        mFragmentDetailBinding.tvPoId.setText("Order Id : "+order.getName());
        mFragmentDetailBinding.tvVendor.setText("Vendor : "+partner);

        mFragmentDetailBinding.tableMain.addView(tableRowHeader,layoutParams);
        tableRowHeader.setBackgroundResource(R.color.android_violet);

        for(int i =0;i<tHeaders.length;i++){
            addHeaderRow(tHeaders[i],tableRowHeader,Color.WHITE);
        }

        for (int i = 0; i <order.getLines().size(); i++) {

//           TableRow.LayoutParams = new TableRow.LayoutParams (TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT,1.0f)
            Line product = order.getLines().get(i);
            total += product.getPriceSubtotal();
            String pid = product.getProductId().get(0).toString(); //instanceof String ? order.getPartnerId().get(1).toString(): order.getPartnerId().get(0).toString();
            String pName = product.getProductId().get(1).toString();
            TableRow trProduct = new TableRow(requireContext());
            trProduct.setBackgroundResource(R.drawable.table_row_bg1);
//            {"Order Id","Product Id","Product Name","Price","product_qty","sequence","price_subtotal"}
            addRow(""+product.getOrderId(),trProduct,Color.BLACK);
//            addRow(pid,trProduct,Color.BLACK);
            addRow(pName,trProduct,Color.BLACK);
            addRow(""+product.getPriceUnit(),trProduct,Color.BLACK);
            addRow(""+product.getProductQty(),trProduct,Color.BLACK);
//            addRow(""+product.getSequence(),trProduct,Color.BLACK);
            addRow(""+product.getPriceSubtotal(),trProduct,Color.BLACK);
            mFragmentDetailBinding.tableMain.addView(trProduct,layoutParams);
        }

        mFragmentDetailBinding.tvTotalPrice.setText("Total: ₹"+total);

    }

    private void addHeaderRow(String str, TableRow row,int color)
    {

        TextView textView = new TextView(requireContext());
        textView.setTextColor(color);
        textView.setText(str);
        textView.setPadding(16,5,16,5);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        row.addView(textView);
    }
    private void addRow(String str, TableRow row,int color)
    {
        TextView textView = new TextView(requireContext());
        textView.setTextColor(color);
        textView.setText(str);
        textView.setPadding(16,5,16,5);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        row.addView(textView);
    }


    private VolleyCallback volleyCallback = new VolleyCallback() {
        @Override
        public void onSuccessResponse(String response, int reqType) {
        if(reqType==1) {

            if(HelperClass.isJSONValid(response)){
                mFragmentDetailBinding.progressCircular.setVisibility(View.GONE);
                mFragmentDetailBinding.tvConfirm.setText("Approved");
                Snackbar.make(requireView(),"Order Successfully Approved",Snackbar.LENGTH_LONG).show();

            }
//            if (HelperClass.isJSONValid(response)) {
//                Gson gson = new Gson();
//                OrderResponse orderResponse = gson.fromJson(response, OrderResponse.class);
//                System.out.println("liss "+orderResponse.getResult().getOrders());
//
//                List  orderItem = new ArrayList<>();
//                for(int i=0;i<orderResponse.getResult().getOrders().size();i++){
//                    Order order =orderResponse.getResult().getOrders().get(i);
//                    if(!order.getState().equalsIgnoreCase("draft")){
//                        orderItem.add(order);
//                    }
//                }
//
//                Snackbar.make(requireView(),"Order Approved Successfully!!",Snackbar.LENGTH_LONG).show();
//
//                Snackbar.make(requireView(),"Orders "+orderItem.size(),Snackbar.LENGTH_LONG).show();
//                System.out.println("uuuu conf -- "+orderResponse.getResult().getOrders());
//
//            }
        }
        }

        @Override
        public void onVolleyError(VolleyError error) {
            Log.d("TAG", "onVolleyError: "+error.getLocalizedMessage());
        }
    };

    private void confirmOrder(){
        mFragmentDetailBinding.progressCircular.setVisibility(View.VISIBLE);
        Object[] args = {
                "casa28dec",
                1,
                "a",
                "dirac.mobile.api",
                "approve_confirm_po",
                "" ,
                order.getId(),

        };

        HelperClass.doJsonRequest(args,"post",1,volleyCallback);
    }


}