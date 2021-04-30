package com.example.odoomobile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.example.odoomobile.application.VolleyCallback;
import com.example.odoomobile.data.purchase.Order;
import com.example.odoomobile.data.purchase.OrderResponse;
import com.example.odoomobile.databinding.FragmentProductBinding;
import com.example.odoomobile.utils.HelperClass;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class ProductFragment extends Fragment implements View.OnClickListener {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private FragmentProductBinding mFragmentProductBinding;
    private NavController navController;

    public ProductFragment() {
        // Required empty public constructor
    }


    public static ProductFragment newInstance(String param1, String param2) {
        ProductFragment fragment = new ProductFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFragmentProductBinding = FragmentProductBinding.inflate(inflater,container,false);
        return mFragmentProductBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        mFragmentProductBinding.relApproval.setOnClickListener(this);
        mFragmentProductBinding.relPurchaseOrder.setOnClickListener(this);
        getOrder();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.relPurchaseOrder:
            case R.id.relApproval:
                navController.navigate(R.id.action_productFragment_to_allFragment);
        }
    }

    private VolleyCallback volleyCallback = new VolleyCallback() {
        @Override
        public void onSuccessResponse(String response, int reqType) {

            if (HelperClass.isJSONValid(response)) {
                mFragmentProductBinding.progressCircular.setVisibility(View.GONE);
                Gson gson = new Gson();
                OrderResponse orderResponse = gson.fromJson(response, OrderResponse.class);
                System.out.println("liss "+orderResponse.getResult().getOrders());

                List orderItem = new ArrayList<>();
                for(int i=0;i<orderResponse.getResult().getOrders().size();i++){
                    Order order =orderResponse.getResult().getOrders().get(i);
                    if(!order.getState().equalsIgnoreCase("draft")){
                        orderItem.add(order);
                    }
                }
                mFragmentProductBinding.orderCount.setText(""+orderItem.size());
            }
        }

        @Override
        public void onVolleyError(VolleyError error) {
            Log.d("TAG", "onVolleyError: "+error.getLocalizedMessage());
        }
    };

    private void getOrder(){
        mFragmentProductBinding.progressCircular.setVisibility(View.VISIBLE);
        Object[] args = {
                "casa28dec",
                1,
                "a",
                "dirac.mobile.api",
                "get_purchase_to_approve",
                ""
        };

        HelperClass.doJsonRequest(args,"post",0,volleyCallback);
    }

}