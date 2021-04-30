package com.example.odoomobile;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.example.odoomobile.adapters.ViewAllAdapter;
import com.example.odoomobile.application.VolleyCallback;
import com.example.odoomobile.data.purchase.Order;
import com.example.odoomobile.data.purchase.OrderResponse;
import com.example.odoomobile.databinding.FragmentAllBinding;
import com.example.odoomobile.models.Purchase;
import com.example.odoomobile.sharedPref.MyPreferences;
import com.example.odoomobile.sharedPref.PrefConf;
import com.example.odoomobile.utils.HelperClass;
import com.example.odoomobile.viewmodels.PurchaseViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AllFragment extends Fragment implements ViewAllAdapter.OrderItemListener{


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentAllBinding mFragmentAllBinding;
    private ViewAllAdapter mAllAdapter;
    private PurchaseViewModel mViewModel;
    private NavController navController;
    private String mParam1;
    private String mParam2;
    private Context mContext;
    private List<Order>orderList;

    public AllFragment() {
        // Required empty public constructor
    }


    public static AllFragment newInstance(String param1, String param2) {
        AllFragment fragment = new AllFragment();
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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof MainActivity){
            mContext = context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            mFragmentAllBinding = FragmentAllBinding.inflate(inflater,container,false);

        return mFragmentAllBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        orderList = new ArrayList<>();
        mAllAdapter = new ViewAllAdapter(this,orderList);
        mFragmentAllBinding.purchaseRecycler.setAdapter(mAllAdapter);
        mViewModel = new ViewModelProvider(requireActivity()).get(PurchaseViewModel.class);

//        mViewModel.getPurchase().observe(requireActivity(), new Observer<List<Purchase>>() {
//            @Override
//            public void onChanged(List<Purchase> purchases) {
//                mAllAdapter.submitList(purchases);
//            }
//        });
        getOrders(0);


    }

    private void getOrders(int type){
        Object[] args = {
                "casa28dec",
                1,
                "a",
                "dirac.mobile.api",
                "get_purchase_to_approve",
                ""
        };
        mFragmentAllBinding.progressCircular.setVisibility(View.VISIBLE);
        mViewModel.getOrder(args,type).observe(requireActivity(), new Observer<OrderResponse>() {
            @Override
            public void onChanged(OrderResponse orderResponse) {

                for(int i=0;i<orderResponse.getResult().getOrders().size();i++){
                    Order order =orderResponse.getResult().getOrders().get(i);
                    if(!order.getState().equalsIgnoreCase("draft")){
                        orderList.add(order);
                    }
                }
                if(orderList.size()>0) {
                    mFragmentAllBinding.purchaseRecycler.setVisibility(View.VISIBLE);
                    mFragmentAllBinding.tvTotalPrice.setVisibility(View.VISIBLE);
                    mFragmentAllBinding.progressCircular.setVisibility(View.GONE);
                    mAllAdapter.updateList(orderList);
                    mFragmentAllBinding.tvTotalPrice.setText("Order Total: ₹" + orderResponse.getResult().getOrdersTotal());
//                    Snackbar.make(requireView(), "Order " + orderResponse.getResult().getOrders().size(), Snackbar.LENGTH_LONG).show();
                }else {
                    mFragmentAllBinding.purchaseRecycler.setVisibility(View.GONE);
                    mFragmentAllBinding.tvTotalPrice.setVisibility(View.GONE);
                }
            }
        });
    }
    @Override
    public void onItemClick(Order order) {
        Gson gson = new Gson();

        String str = gson.toJson(order);
        MyPreferences.getInstance(requireActivity()).putString(PrefConf.PURCHASE,str);

        navController.navigate(R.id.action_allFragment_to_detailFragment);

    }

    @Override
    public void onConfirm(Order order) {
        mFragmentAllBinding.progressCircular.setVisibility(View.VISIBLE);
            if(order.getState().equalsIgnoreCase("draft"))
                return;

        Object[] args = {
                "casa28dec",
                1,
                "a",
                "dirac.mobile.api",
                "approve_confirm_po",
                "" ,
                order.getId(),

        };
        mViewModel.confirmOrder(args,1).observe(requireActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){

                    Object[] arg = {
                            "casa28dec",
                            1,
                            "a",
                            "dirac.mobile.api",
                            "get_purchase_to_approve",
                            ""
                    };

                    HelperClass.doJsonRequest(arg,"post",3,volleyCallback);

//                    mViewModel.getOrder(arg,0).observe(requireActivity(), new Observer<OrderResponse>() {
//                        @Override
//                        public void onChanged(OrderResponse orderResponse) {
//
//                          List  orderItem = new ArrayList<>();
//                            for(int i=0;i<orderResponse.getResult().getOrders().size();i++){
//                                Order order =orderResponse.getResult().getOrders().get(i);
//                                if(!order.getState().equalsIgnoreCase("draft")){
//                                    orderItem.add(order);
//                                }
//                            }
//                            mAllAdapter.updateList(orderItem);
//                            mFragmentAllBinding.tvTotalPrice.setText("Order Total: ₹"+orderResponse.getResult().getOrdersTotal());
//                            mFragmentAllBinding.progressCircular.setVisibility(View.GONE);
//                            Snackbar.make(requireView(),"Order Approved Successfully!!",Snackbar.LENGTH_LONG).show();
//
//                            Snackbar.make(requireView(),"Orders "+orderItem.size(),Snackbar.LENGTH_LONG).show();
//                System.out.println("uuuu conf -- "+orderResponse.getResult().getOrders());
//                        }
//                    });
                }
            }
        });

    }
    
    
private VolleyCallback volleyCallback = new VolleyCallback() {
    @Override
    public void onSuccessResponse(String response, int reqType) {
//        if(reqType==0) {
            if (HelperClass.isJSONValid(response)) {
                Gson gson = new Gson();
                OrderResponse orderResponse = gson.fromJson(response, OrderResponse.class);
                System.out.println("liss "+orderResponse.getResult().getOrders());

                List  orderItem = new ArrayList<>();
                for(int i=0;i<orderResponse.getResult().getOrders().size();i++){
                    Order order =orderResponse.getResult().getOrders().get(i);
                    if(!order.getState().equalsIgnoreCase("draft")){
                        orderItem.add(order);
                    }
                }
                if(orderItem.size()>0) {
                    mFragmentAllBinding.purchaseRecycler.setVisibility(View.VISIBLE);
                    mFragmentAllBinding.tvTotalPrice.setVisibility(View.VISIBLE);
                    mAllAdapter.updateList(orderItem);
                    mFragmentAllBinding.tvTotalPrice.setText("Order Total: ₹" + orderResponse.getResult().getOrdersTotal());
                    mFragmentAllBinding.progressCircular.setVisibility(View.GONE);
                    Snackbar.make(requireView(), "Order Approved Successfully!!", Snackbar.LENGTH_LONG).show();
                }else {
                    mFragmentAllBinding.purchaseRecycler.setVisibility(View.GONE);
                    mFragmentAllBinding.tvTotalPrice.setVisibility(View.GONE);
                }
//                Snackbar.make(requireView(),"Orders "+orderItem.size(),Snackbar.LENGTH_LONG).show();
//                System.out.println("uuuu conf -- "+orderResponse.getResult().getOrders());

            }
//        }else {
//            if(HelperClass.isJSONValid(response)){
//                booleanMutableLiveData.setValue(true);
//
//            }
//        }
    }

    @Override
    public void onVolleyError(VolleyError error) {
        Log.d("TAG", "onVolleyError: "+error.getLocalizedMessage());
    }
};



}