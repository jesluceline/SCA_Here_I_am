package com.example.sca_wifilist;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    WifiManager wifiManager;
    WifiReciever wifiReciever;
    ListAdapter listAdapter;
    ListView wifiList;
    List mywifiList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wifiList = (ListView)findViewById(R.id.myListView);

        wifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
        wifiReciever = new WifiReciever();

        registerReceiver(wifiReciever,new IntentFilter((WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)));

        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION},0);
        }

        else {
            scanWifiList();
        }


    }

    private void scanWifiList() {
        wifiManager.startScan();
        mywifiList = wifiManager.getScanResults();
        setAdapter();
    }

    private void setAdapter() {
        listAdapter= new ListAdapter(getApplicationContext(), mywifiList);
        wifiList.setAdapter(listAdapter);
    }

    class WifiReciever extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }
}