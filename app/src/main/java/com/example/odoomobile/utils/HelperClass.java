package com.example.odoomobile.utils;

import android.app.NotificationManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.odoomobile.application.OdooApplication;
import com.example.odoomobile.application.VolleyCallback;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by mohammedragab on 8/7/17.
 */

public class HelperClass {
    private static final String TAG = "HelperClass";
    public static final String LOGIN = "LOGIN";
    public static final String POST = "POST";
    public static final String CREATE = "CREATE";
//    DCS_Deliverable_Control_System_2018-01-11
//    DCS_Deliverable_Control_System_2017-12-13/ previous one When I pull the code.
//    DCS_Deliverable_Control_System
//    DCS_Deliverable_Control_System_2018-09-15
    public static final String DB_NAME = "DCS_Deliverable_Control_System"; //Live _2018-09-16
    public static final String METHOD_MY_WORK_TODAY = "get_activity";
    public static final String METHOD_RESPONSIBILITY = "get_mydcs_responsibility";
    public static final String METHOD_MY_REQUESTS = "get_portal_requester";
    public static final String METHOD_ISSUES = "get_issues";
    public static final String METHOD_DEPENDENCY = "get_dependency";
    public static final String METHOD_RISK = "get_risk";
    public static final String METHOD_TIMESHEET = "get_timesheet";
    public static final String METHOD_DCS_LOGIN = "login";


    public static final String METHOD_EDIT_ASSIGMNENT = "team_leader_edit_request";
    public static final String METHOD_APPROVE_REJECT_ASSIGMNENT = "approve_reject_tickets";

    public static final String METHOD_SET_DEVICE_KEY = "set_device_key";

    public static final String METHOD_NEW_ISSUE = "issues_create";
    public static final String METHOD_NEW_DEPENDENCY = "dependency_create";
    public static final String METHOD_NEW_RISK = "risk_create";

    public static final String METHOD_GET_FEEDBACK_DATA = "get_feedback_data";

    public static final String METHOD_CREATE_TIMESHEET = "timesheet_create";

    public static final String METHOD_UPDATE_TIME_SPENT = "timesheet_update";
    public static final String METHOD_SEND_UPDATE = "send_updates";
    public static final String METHOD_DELIVERABLE_UPDATE = "deliverable_update";
    public static final String METHOD_ESTIMATE_DTIME_CHANGE = "estimated_change";
    public static final String METHOD_USER_PROFILE = "get_profile";
    public static final String METHOD_USER_PROFILE_UPDATE = "update_profile";
    public static final String METHOD_UPDATE_START_DATE = "deliverable_inprogress";

    public static final String METHOD_TICKET_INFO = "get_ticket_details";
    public static final String METHOD_UPDATE_OUTCOME = "task_outcome_update";
    public static final String  METHOD_WORK_REPORT  = "get_work_report";
    public static final String  METHOD_GET_EMPLOYEE  = "get_employee";



//    deliverable_inprogress, deliverable_completion, deliverable_stand_by

    public static final String METHOD_STATUS_IN_PROGRESS = "deliverable_inprogress";
    public static final String METHOD_STATUS_COMPLETED = "deliverable_completion";
    public static final String METHOD_STATUS_STAND_BY = "deliverable_stand_by";


    public static final String METHOD_DEPARTMENT_EMPLOYEE = "get_depart_employee";
    public static final String METHOD_NEW_REQUEST = "dcs_create_request";
    public static final String METHOD_NEW_ASSIGNMENT = "dcs_request_ticket";

    public static final String METHOD_COMPLETED = "task_feedback";
    public static final String METHOD_CHAT_CREATE = "message_create";
    public static final String METHOD_GET_CHAT = "get_messages";
    public static final String METHOD_UPDATE_PASSWORD = "reset_password";
    public static final String METHOD_USER_SIGNUP = "create";

    public static final String OUTCOME = "OUTCOME";
    public static final String ID = "ID";
    public static final String REQUEST_ID = "REQUEST_ID";

    // Messages
    public static final String ERROR_INTERNET = "Check Your Internet";
    public static final String ERROR_CONNECTION = "Connection refused \n Failed to connect to Server, Ask Support";
    public static final String PULL_TO_REFRESH = "PULL TO REFRESH";
    public static final String ERROR_SEND_ATTACHMENT = "This Attachment can't be send, Please try again";


    public static final Map<Integer, Integer> RATING;
    public static final Map<String, String> FREQUENTLY_TIME_MAP;
    public static final Map<String, String> STATE;
    public static final Map<String, String> STATE_UPDATE;
    public static final Map<String, String> PRIORITY; //{"Low", "Normal", "High", "Critical"};
//    public static final List<Id_Name> FREQUENTLY_TIME;
//    public static final Map<Integer, String> TAG_Activities;
    public static final Map<String, String> DEPARTMENT_TYPE;


     private static final String BASE_URL = "http://167.71.233.174:1100/jsonrpc";//"http://dcs.fore-vision.com:9090/jsonrpc";
  //  private static final String BASE_URL = "http://134.136.39.114:8089/jsonrpc"; // Adnan PC

//    static {
//        TAG_Activities = new HashMap<>();
//        TAG_Activities.put(R.id.ll_completed, "Completed");
//        TAG_Activities.put(R.id.ll_assigned, "Assigned Not Started");
//        TAG_Activities.put(R.id.ll_critical, "Critical");
//        TAG_Activities.put(R.id.ll_delayed, "Delayed");
//        TAG_Activities.put(R.id.ll_inprogress, "In Progress");
//        TAG_Activities.put(R.id.ll_pending_rating, "Pending Acceptance");
//        TAG_Activities.put(R.id.ll_rated, "Rated");
//        TAG_Activities.put(R.id.ll_unapproved, "Un Approved");
//
//        TAG_Activities.put(R.id.issues, "Issues");
//        TAG_Activities.put(R.id.dependacy, "Dependencies");
//        TAG_Activities.put(R.id.risk, "Risks");
//    }

    static {

        RATING = new HashMap<>();
        RATING.put(1, 1);
        RATING.put(0, 2);
        RATING.put(-1, 3);
    }

    static {

        DEPARTMENT_TYPE = new HashMap<>();
        DEPARTMENT_TYPE.put("", "");
        DEPARTMENT_TYPE.put("", "");
        DEPARTMENT_TYPE.put("", "");
    }
    static {
        FREQUENTLY_TIME_MAP = new HashMap<>();
        FREQUENTLY_TIME_MAP.put("N/A", "0");
        FREQUENTLY_TIME_MAP.put("1 Day", "1");
        FREQUENTLY_TIME_MAP.put("2 Days", "2");
        FREQUENTLY_TIME_MAP.put("3 Days", "3");
        FREQUENTLY_TIME_MAP.put("4 Days", "4");
        FREQUENTLY_TIME_MAP.put("5 Days", "5");
        FREQUENTLY_TIME_MAP.put("7 Days", "7");
        FREQUENTLY_TIME_MAP.put("10 Days", "10");
        FREQUENTLY_TIME_MAP.put("15 Days", "15");
        FREQUENTLY_TIME_MAP.put("20 Days", "20");
        FREQUENTLY_TIME_MAP.put("30 Days", "30");

        FREQUENTLY_TIME_MAP.put("0", "N/A");
        FREQUENTLY_TIME_MAP.put("1", "1 Day");
        FREQUENTLY_TIME_MAP.put("2", "2 Days");
        FREQUENTLY_TIME_MAP.put("3", "3 Days");
        FREQUENTLY_TIME_MAP.put("4", "4 Days");
        FREQUENTLY_TIME_MAP.put("5", "5 Days");
        FREQUENTLY_TIME_MAP.put("7", "7 Days");
        FREQUENTLY_TIME_MAP.put("10", "10 Days");
        FREQUENTLY_TIME_MAP.put("15", "15 Days");
        FREQUENTLY_TIME_MAP.put("20", "20 Days");
        FREQUENTLY_TIME_MAP.put("30", "30 Days");
    }

    static {
        STATE = new HashMap<>();
        STATE.put("drafts", "Not Assigned");
        STATE.put("draft", "Not started");
        STATE.put("open", "In Progress");
        STATE.put("pending", "Complete");
        STATE.put("pendingF", "Complete");
        STATE.put("missed", "Missed");
        STATE.put("done", "Closed");
        STATE.put("aborted", "Aborted");
        STATE.put("stand_by", "Stand by");

    }

    //    deliverable_inprogress, deliverable_completion, deliverable_stand_by
    static {
        STATE_UPDATE = new HashMap<>();
        STATE_UPDATE.put("drafts", "Not Assigned");
        STATE_UPDATE.put("draft", "Not started");
        STATE_UPDATE.put("open", "deliverable_inprogress");
        STATE_UPDATE.put("pending", "deliverable_completion");
        STATE_UPDATE.put("pendingF", "Complete");
        STATE_UPDATE.put("missed", "Missed");
        STATE_UPDATE.put("done", "Closed");
        STATE_UPDATE.put("aborted", "Aborted");
        STATE_UPDATE.put("stand_by", "Stand by");

    }

    static {
        PRIORITY = new HashMap<>();
        PRIORITY.put("0", "Low");
        PRIORITY.put("1", "Normal");
        PRIORITY.put("2", "High");
        PRIORITY.put("3", "Critical");
    }

//    static {
//        FREQUENTLY_TIME = new ArrayList<>();
//
//        FREQUENTLY_TIME.add(new Id_Name(0, "N/A"));
//        FREQUENTLY_TIME.add(new Id_Name(1, "1 Day"));
//        FREQUENTLY_TIME.add(new Id_Name(2, "2 Days"));
//        FREQUENTLY_TIME.add(new Id_Name(3, "3 Days"));
//        FREQUENTLY_TIME.add(new Id_Name(4, "4 Days"));
//        FREQUENTLY_TIME.add(new Id_Name(5, "5 Days"));
//        FREQUENTLY_TIME.add(new Id_Name(7, "7 Days"));
//        FREQUENTLY_TIME.add(new Id_Name(10, "10 Days"));
//        FREQUENTLY_TIME.add(new Id_Name(15, "15 Days"));
//        FREQUENTLY_TIME.add(new Id_Name(20, "20 Days"));
//        FREQUENTLY_TIME.add(new Id_Name(30, "30 Days"));
//
//    }

    public static List<Date> getDates(String dateString1) {
        ArrayList<Date> dates = new ArrayList<Date>();
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        Date date1 = null;

        try {
            date1 = df1.parse(dateString1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        cal1.set(Calendar.DAY_OF_MONTH, cal1.get(Calendar.DAY_OF_MONTH) - 6);


        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date1);
//        cal2.set(Calendar.DAY_OF_MONTH, cal2.get(Calendar.DAY_OF_MONTH)-6);

        while (!cal1.after(cal2)) {
            dates.add(cal1.getTime());
            cal1.add(Calendar.DATE, 1);
        }
        return dates;
    }

    public static String ApiRequest(String Flag, Object[] args0) throws IOException {

        Map<String, Object> args = new HashMap<>();
        Map<String, Object> paramsMap = new HashMap<>();

        if (Flag == LOGIN) {
            args.put("service", "common");
            args.put("args", args0);
            args.put("method", "login");

        } else {
            args.put("args", args0);
            args.put("service", "object");
            args.put("method", "execute");

        }


        paramsMap.put("id", "1");
        paramsMap.put("jsonrpc", "2.0");
        if (Flag == CREATE) {
            paramsMap.put("service", "object");
            paramsMap.put("method", "execute");
        } else {
            paramsMap.put("method", "call");
        }

        paramsMap.put("params", args);


        String json = new GsonBuilder()
                .enableComplexMapKeySerialization()
                .setPrettyPrinting()
                .create().toJson(paramsMap, Map.class);

        URL url = new URL(BASE_URL);
        HttpURLConnection uc = (HttpURLConnection) url.openConnection();
        String line;
        StringBuffer jsonString = new StringBuffer();

        uc.setRequestProperty("Content-Type", "application/json-rpc; charset=UTF-8");
        uc.setRequestMethod("POST");

        uc.setDoInput(true);
        uc.setInstanceFollowRedirects(false);
        uc.setConnectTimeout(20000);
        uc.setReadTimeout(30000);
        uc.connect();

        OutputStreamWriter writer = new OutputStreamWriter(uc.getOutputStream(), "UTF-8");

        writer.write(json);
        writer.close();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            while ((line = br.readLine()) != null) {
                jsonString.append(line);
            }
            br.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        uc.disconnect();
//        if(isJSONValid(jsonString.toString()))
//        pDialog.show();
        return jsonString.toString();
//        return "";
    }

    public static boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            // edited, to include @Arthur's comment
            // e.g. in case JSONArray is valid as well...
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activityNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return !(activityNetworkInfo != null && activityNetworkInfo.isConnected());
    }

    public static void cancelAllNotification(Context ctx) {
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nMgr = (NotificationManager) ctx.getSystemService(ns);
        nMgr.cancelAll();

//        GlobalVars.getInstance().setArr_ticket(new ArrayList<Ticket>());
//        GlobalVars.getInstance().setTicket(new Ticket());
//        GlobalVars.getInstance().setUser(new User());
//        GlobalVars.getInstance().setLoggedIn(false);
    }

    public static void doStringRequest(Object[] args0,String Flag, final int type , final VolleyCallback callback) {
        final String tag_string_req = "volley_req";



        StringRequest strReq = new StringRequest(Request.Method.POST,BASE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(type==0){
                    callback.onSuccessResponse(response,type);
                }else{
                    callback.onSuccessResponse(response,type);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: "+error);
                callback.onVolleyError(error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
              
                Map<String, String> paramsMap = new HashMap<>();
            
                JSONObject jsonObject = new JSONObject();
                JSONObject jsonObjectParams = new JSONObject();
                JSONArray params = new JSONArray();



                try {
                    for(int i=0;i<args0.length;i++){
                        params.put(args0[i]);
                    }



                    if (Flag == LOGIN) {

                        jsonObjectParams.put("service", "common");
                        jsonObjectParams.put("args", params);
                        jsonObjectParams.put("method", "login");

                    } else {
                        jsonObjectParams.put("args", params);
                        jsonObjectParams.put("service", "object");
                        jsonObjectParams.put("method", "execute");

                    }


                    jsonObject.put("id", "1");
                    jsonObject.put("jsonrpc", "2.0");
                    if (Flag == CREATE) {
                        jsonObject.put("service", "object");
                        jsonObject.put("method", "execute");
                    } else {
                        jsonObject.put("method", "call");
                    }
                    jsonObject.put("params", jsonObjectParams);
                }catch (JSONException je){
                    je.printStackTrace();
                }
               
              

//                String json = new GsonBuilder()
//                        .enableComplexMapKeySerialization()
//                        .setPrettyPrinting()
//                        .create().toJson(paramsMap, Map.class);
                Log.d(TAG, "getParams: "+jsonObject.toString());
                paramsMap.put("params",jsonObject.toString());
                return paramsMap;
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=UTF-8";
            }
        };



        OdooApplication.getInstance().addToRequestQueue(strReq, tag_string_req);

    }

    public static void  doJsonRequest(Object[] args0,String Flag, final int type , final VolleyCallback callback){
        final String tag_string_req = "volley_req";
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObjectParams = new JSONObject();
        JSONArray params = new JSONArray();

        try {
            for(int i=0;i<args0.length;i++){
                params.put(args0[i]);
            }
                jsonObjectParams.put("args", params);
                jsonObjectParams.put("service", "object");
                jsonObjectParams.put("method", "execute");

//            if (Flag == LOGIN) {
//
//                jsonObjectParams.put("service", "common");
//                jsonObjectParams.put("args", params);
//                jsonObjectParams.put("method", "login");
//
//            } else {
//                jsonObjectParams.put("args", params);
//                jsonObjectParams.put("service", "object");
//                jsonObjectParams.put("method", "execute");
//
//            }



            jsonObject.put("jsonrpc", "2.0");
            jsonObject.put("method", "call");
            jsonObject.put("id", "1");
//            if (Flag == CREATE) {
//                jsonObject.put("service", "object");
//                jsonObject.put("method", "execute");
//            } else {
//                jsonObject.put("method", "call");
//            }
            jsonObject.put("params", jsonObjectParams);
        }catch (JSONException je){
            je.printStackTrace();
        }
        Log.d(TAG, "doJsonRequest:postData "+jsonObject.toString());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, BASE_URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "onResponse: type "+type+""+response.toString());
                callback.onSuccessResponse(response.toString(),type);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: "+error);
                callback.onVolleyError(error);
            }
        });
        OdooApplication.getInstance().addToRequestQueue(jsonObjectRequest, tag_string_req);
    }

//    {
//        @Override
//        protected Map<String, String> getParams() throws AuthFailureError {
//
//        Map<String, String> paramsMap = new HashMap<>();
//
//        JSONObject jsonObject = new JSONObject();
//        JSONObject jsonObjectParams = new JSONObject();
//        JSONArray params = new JSONArray();
//
//
//
//        try {
//            for(int i=0;i<args0.length;i++){
//                params.put(args0[i]);
//            }
//
//
//
//            if (Flag == LOGIN) {
//
//                jsonObjectParams.put("service", "common");
//                jsonObjectParams.put("args", params);
//                jsonObjectParams.put("method", "login");
//
//            } else {
//                jsonObjectParams.put("args", params);
//                jsonObjectParams.put("service", "object");
//                jsonObjectParams.put("method", "execute");
//
//            }
//
//
//            jsonObject.put("id", "1");
//            jsonObject.put("jsonrpc", "2.0");
//            if (Flag == CREATE) {
//                jsonObject.put("service", "object");
//                jsonObject.put("method", "execute");
//            } else {
//                jsonObject.put("method", "call");
//            }
//            jsonObject.put("params", jsonObjectParams);
//        }catch (JSONException je){
//            je.printStackTrace();
//        }
//
//
//
////                String json = new GsonBuilder()
////                        .enableComplexMapKeySerialization()
////                        .setPrettyPrinting()
////                        .create().toJson(paramsMap, Map.class);
//        Log.d(TAG, "getParams: "+jsonObject.toString());
//        paramsMap.put("params",jsonObject.toString());
//        return paramsMap;
//    }
//
//        @Override
//        public String getBodyContentType() {
//        return "application/json; charset=UTF-8";
//    }
//    };

}
