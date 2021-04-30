package com.example.odoomobile.application;

import com.android.volley.VolleyError;

public interface VolleyCallback {

    void onSuccessResponse(String response,int reqType);

    void onVolleyError(VolleyError error);

}
