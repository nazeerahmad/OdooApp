package com.example.odoomobile.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.odoomobile.data.purchase.OrderResponse;
import com.example.odoomobile.models.Purchase;
import com.example.odoomobile.repositories.PurchaseRepo;

import java.util.List;

public class PurchaseViewModel extends ViewModel {


    private PurchaseRepo  purchaseRepo= new PurchaseRepo();

    public MutableLiveData<List<Purchase>> getPurchase(){
                return purchaseRepo.getPurchase();
    }

    public MutableLiveData<OrderResponse> getOrder(Object [] args,int type){
        return purchaseRepo.getOrder(args,type);
    }

    public MutableLiveData<Boolean> confirmOrder(Object [] arg,int type){
        return purchaseRepo.confirmOrder(arg,type);
    }



}
