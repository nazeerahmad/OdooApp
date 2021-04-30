package com.example.odoomobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.odoomobile.databinding.ActivityLoginBinding;
import com.example.odoomobile.sharedPref.MyPreferences;
import com.example.odoomobile.sharedPref.PrefConf;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import oogbox.api.odoo.OdooClient;
import oogbox.api.odoo.OdooUser;
import oogbox.api.odoo.client.AuthError;
import oogbox.api.odoo.client.OdooVersion;
import oogbox.api.odoo.client.helper.OdooErrorException;
import oogbox.api.odoo.client.helper.data.OdooRecord;
import oogbox.api.odoo.client.helper.data.OdooResult;
import oogbox.api.odoo.client.listeners.AuthenticateListener;
import oogbox.api.odoo.client.listeners.IOdooResponse;
import oogbox.api.odoo.client.listeners.OdooConnectListener;
import oogbox.api.odoo.client.listeners.OdooErrorListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private OdooClient odooClient;
    private ActivityLoginBinding mActivityLoginBinding;
    private List<String> dbList;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       mActivityLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
       View view = mActivityLoginBinding.getRoot();
       setContentView(view);
       mContext = LoginActivity.this;

        odooClient = new OdooClient.Builder(mContext)
                .setHost(createServerURL(PrefConf.HOST_URL))
                .setErrorListener(new OdooErrorListener() {
                    @Override
                    public void onError(OdooErrorException error) {
                        Toast.makeText(mContext,error.getLocalizedMessage(),Toast.LENGTH_LONG).show();

                    }
                })
                .setConnectListener(new OdooConnectListener() {
                    @Override
                    public void onConnected(OdooVersion version) {

                        Toast.makeText(mContext,"Odoo "+version.server_version +" connected",Toast.LENGTH_LONG).show();
                    }
                }).build();

        mActivityLoginBinding.btnLogin.setOnClickListener(this);
        mActivityLoginBinding.forgotPassword.setOnClickListener(this);
        mActivityLoginBinding.createAccount.setOnClickListener(this);
    }
    private void userLogin(String userName,String pass){
        dbList = new ArrayList<>();
        if(odooClient.isConnected()){
            odooClient.getDatabases(new IOdooResponse() {
                @Override
                public void onResult(OdooResult result) {
                    dbList = result.getArray("result");
                    odooClient.authenticate(userName,pass,dbList.get(0),loginListener);
                }
            });


            List<Integer> ids = Arrays.asList(1, 2, 3,4,5,6,7,8,9);
            List<String> fields = Arrays.asList("id", "name");

//            odooClient.read("res.partner", ids, fields, new IOdooResponse() {
//                @Override
//                public void onResult(OdooResult result) {
//                    OdooRecord[] records = result.getRecords();
//                    List<OdooRecord> list = new ArrayList<>(Arrays.asList(records));
//                    System.out.println("lisss "+list+" "+list.size());
////                    for(OdooRecord record: records) {
////                        Log.v("Name:", record.getString("name"));
////                    }
//                }
//            });
        }


    }
    private boolean validateURL(String url) {
        return (url.contains("."));
    }

    private String createServerURL(String server_url) {
        StringBuilder serverURL = new StringBuilder();
        if (!server_url.contains("http://") && !server_url.contains("https://")) {
            serverURL.append("http://");
        }
        serverURL.append(server_url);
        return serverURL.toString();
    }


    AuthenticateListener loginListener = new AuthenticateListener() {
        @Override
        public void onLoginSuccess(OdooUser user) {

            System.out.println("seess "+ odooClient.getSessionId());
            Gson gson = new Gson();

            String odooUser = gson.toJson(user);
            MyPreferences.getInstance(mContext).putString(PrefConf.ODOOUSER,odooUser);
            MyPreferences.getInstance(mContext).putBoolean(PrefConf.KEY_IS_LOGGED_IN,true);

            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        }

        @Override
        public void onLoginFail(AuthError error) {
            Toast.makeText(mContext,error.name(),Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLogin:
                String user = mActivityLoginBinding.edtUserName.getText().toString();
                String pass = mActivityLoginBinding.edtPassword.getText().toString();
                if(user.equalsIgnoreCase("")){
                    mActivityLoginBinding.edtUserName.setError("Please Enter User Name!");
                }else if(pass.equalsIgnoreCase("")){
                    mActivityLoginBinding.edtPassword.setError("Password can not be empty");
                }else {
                    userLogin(user,pass);
                }
                break;
            case R.id.forgot_password:
                Toast.makeText(mContext,"ToDo",Toast.LENGTH_LONG).show();
                break;
            case R.id.create_account:
                Toast.makeText(mContext,"pending",Toast.LENGTH_LONG).show();
                break;
        }
    }
}