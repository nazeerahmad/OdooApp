package com.example.odoomobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.odoomobile.databinding.ActivityMainBinding;
import com.example.odoomobile.sharedPref.MyPreferences;
import com.example.odoomobile.sharedPref.PrefConf;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Type;

import oogbox.api.odoo.OdooUser;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ActivityMainBinding mActivityMainBinding;
    private OdooUser odooUser;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mActivityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mActivityMainBinding.getRoot();
        setContentView(view);
        navController = Navigation.findNavController(this,R.id.nav_host_fragment);
        NavigationUI.setupWithNavController( mActivityMainBinding.toolbar, navController,mActivityMainBinding.drawerLayout);
        mActivityMainBinding.navView.setNavigationItemSelectedListener(this);
        setData();
        mActivityMainBinding.navView.setItemIconTintList(null);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }


//    public static void setUpDrawer(Activity activity, DrawerLayout drawer, Toolbar toolbar) {
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(activity, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//    }

    private void setData() {
        Gson gson = new Gson();
        Type type = new TypeToken<OdooUser>() {
        }.getType();
        View view = mActivityMainBinding.navView.getHeaderView(0);
        TextView tvIp = view.findViewById(R.id.tv_head_nav_ip);
        TextView tvUser = view.findViewById(R.id.tv_head_nav_user_name);
        ImageView profile = view.findViewById(R.id.img_profile_pic);
        odooUser = gson.fromJson(MyPreferences.getInstance(this).getString(PrefConf.ODOOUSER, ""), type);
        tvUser.setText(odooUser.username);
        tvIp.setText(createServerURL(PrefConf.HOST_URL));
    }

    private String createServerURL(String server_url) {
        StringBuilder serverURL = new StringBuilder();
        if (!server_url.contains("http://") && !server_url.contains("https://")) {
            serverURL.append("http://");
        }
        serverURL.append(server_url);
        return serverURL.toString();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_purchase:
            case  R.id.nav_approval:
                navController.navigate(R.id.action_productFragment_to_allFragment);
                break;
            case R.id.nav_log_out:
                MyPreferences.getInstance(this).clearPreferences();
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;

        }
        mActivityMainBinding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mActivityMainBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            mActivityMainBinding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }
    @Override
    public boolean onSupportNavigateUp() {
        navController.navigateUp();
        return super.onSupportNavigateUp();
    }
}