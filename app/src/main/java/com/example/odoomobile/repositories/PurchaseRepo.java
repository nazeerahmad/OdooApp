package com.example.odoomobile.repositories;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.VolleyError;
import com.example.odoomobile.AllFragment;
import com.example.odoomobile.application.VolleyCallback;
import com.example.odoomobile.data.purchase.OrderResponse;
import com.example.odoomobile.models.Product;
import com.example.odoomobile.models.Purchase;
import com.example.odoomobile.utils.HelperClass;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PurchaseRepo {
    private static final String TAG = "PurchaseRepo";
    private MutableLiveData<List<Purchase>> mutableLivePurchase;
    private MutableLiveData<OrderResponse> mutableLiveOrder;

    private List<Product>products;
    private List<Purchase>purchases;
    private String[] vendor ={"Abeel","Abidia","Treequote","Ron-tech","Tech Enterprises"};
    private String[] dates ={"12/16/2020","12/31/2020","11/17/2020","01/05/2021","10/14/2020"};
    private String[] name ={"USB Adapter","Wireless headPhone","Honor 9Lite","MI Note11","IPhone Max"};
//    private ApiCallTask apiCallTask;
    public MutableLiveData<List<Purchase>> getPurchase(){
        if(mutableLivePurchase == null){
            mutableLivePurchase = new MutableLiveData<>();
            loadProducts();

        }
        return mutableLivePurchase;
    }

    private void loadProducts() {
        products = new ArrayList<>();
        purchases = new ArrayList<>();
        for(int i=0;i<5;i++){
            Product product = new Product("P83"+i,name[i],1200*i);
            products.add(product);
        }
        if(products.size()>0) {
            for (int i = 0; i <products.size(); i++) {
                Purchase purchase = new Purchase(i,"PO-883-3"+i,dates[i],vendor[i],i,products.get(i).getPrice()*(i+1),products);
                purchases.add(purchase);
            }
        }
        mutableLivePurchase.setValue(purchases);
    }

    public MutableLiveData<OrderResponse> getOrder(Object [] args,int type){
        mutableLiveOrder=null;
        if(mutableLiveOrder == null){
            mutableLiveOrder = new MutableLiveData<>();
//            callOrder(args,type);
            HelperClass.doJsonRequest(args,"Get",type,callback);
        }
        return mutableLiveOrder;
    }

    public MutableLiveData<Boolean> booleanMutableLiveData;
    public MutableLiveData<Boolean> confirmOrder(Object [] args,int type){
        if(booleanMutableLiveData == null){
            booleanMutableLiveData = new MutableLiveData<>();
//            callConfirm(args,type);
            HelperClass.doJsonRequest(args,"Get",type,callback);
        }
        return booleanMutableLiveData;
    }

//    public void callConfirm(Object [] arg,int type) {
//        if(apiCallTask == null) {
//            apiCallTask = new ApiCallTask(arg, type);
//            apiCallTask.execute("");
//        }
//    }
//    public void callOrder(Object [] arg,int type) {
//        if(apiCallTask==null) {
//          apiCallTask = new ApiCallTask(arg, type);
//          apiCallTask.execute("");
//        }
//    }

//    public class ApiCallTask extends AsyncTask<String, Void, String> {
//        Object [] args;
//        int reqType;
//        public ApiCallTask(Object [] arg,int type){
//            args= arg;
//            reqType= type;
//        }
//
//        @Override
//        protected String doInBackground(String... data) {
//
//            String jsonResp="";
////            Object[] args = {
////                    "casa28dec",
////                    1,
////                    "a",
////                    "dirac.mobile.api",
////                    "get_purchase_to_approve",
////                    ""
////            };
//            try {
//                jsonResp = HelperClass.ApiRequest("GET",args);
//
//            } catch (IOException e) {
//                System.out.println("jsonResp "+e.getLocalizedMessage());
//                e.printStackTrace();
//            }
//            return jsonResp;
//        }
//
//        @Override
//        protected void onPostExecute(String json) {
//            super.onPostExecute(json);
//            System.out.println("jsonResp "+json);
//            if(reqType==0) {
//                if (HelperClass.isJSONValid(json)) {
//                    Gson gson = new Gson();
//                    OrderResponse orderResponse = gson.fromJson(json, OrderResponse.class);
//                    System.out.println("liss "+orderResponse.getResult().getOrders());
//                    mutableLiveOrder.setValue(orderResponse);
//                }
//            }else {
//                if(HelperClass.isJSONValid(json)){
//                    booleanMutableLiveData.setValue(true);
//
//                }
//            }
//
//            apiCallTask = null;
//        }
//    }

    private VolleyCallback callback = new VolleyCallback() {
        @Override
        public void onSuccessResponse(String response,int reqType) {
            Log.d(TAG, "onSuccessResponse: "+response +" "+reqType);
            if(reqType==0) {
                if (HelperClass.isJSONValid(response)) {

                    Gson gson = new Gson();

                    OrderResponse orderResponse = gson.fromJson(response, OrderResponse.class);
                    System.out.println("liss "+orderResponse.getResult().getOrders());
                    mutableLiveOrder.setValue(orderResponse);
                }
            }else {
                if(HelperClass.isJSONValid(response)){
                    booleanMutableLiveData.setValue(true);

                }
            }

        }

        @Override
        public void onVolleyError(VolleyError error) {
            Log.d(TAG, "onVolleyError: "+error.getLocalizedMessage());
        }
    };
}
