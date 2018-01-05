package com.stc.game2d;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.aware.WifiAwareManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ConnectActivity extends Activity{
    private static final String TAG = "ConnectActivity";
    private BroadcastReceiver wifiReceiver;
    private WifiAwareManager wifiAwareManager;
    TextView textStatus;
    private WifiAwareHelper wifiAwareHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        textStatus=findViewById(R.id.textView);
        //wifiAwareHelper = new WifiAwareHelper(this);

        subscribeToWifiUpdates();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(wifiReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();


    }
    public void updateStatus(String text){
        Log.w(TAG, "updateStatus: "+text);
        textStatus.setText(text);
    }

    private void subscribeToWifiUpdates() {
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_WIFI_AWARE)) {
            updateStatus("wifi aware not available");
        }else {
            Log.w(TAG, "subscribeToWifiUpdates: FEATURE_WIFI_AWARE ok" );

            IntentFilter filter =
                    new IntentFilter(WifiAwareManager.ACTION_WIFI_AWARE_STATE_CHANGED);
            wifiReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(final Context context, final Intent intent) {
                    Log.w(TAG, "onReceive: isAvailable = "+wifiAwareManager.isAvailable() );
                    if (wifiAwareManager.isAvailable()) {
                        init();
                    } else {
                        updateStatus("Wifi not available");
                        stopAllConnections();
                    }
                }
            };
            registerReceiver(wifiReceiver, filter);

            wifiAwareManager = (WifiAwareManager) getApplicationContext().getSystemService(WIFI_AWARE_SERVICE);
            if (wifiAwareManager != null) {

                Log.w(TAG, "subscribeToWifiUpdates: isAvailable "+ wifiAwareManager.isAvailable());
                if (wifiAwareManager.isAvailable()) {
                    init();
                }
            }else Log.w(TAG, "subscribeToWifiUpdates: null" );
        }
    }

    private void stopAllConnections() {
        updateStatus("not connected");
    }

    private void init() {
        updateStatus("loading");
        showDialogConnect();
    }
    private void showDialogConnect(){
        Log.d(TAG, "showDialogConnect() called");

        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.connect_dialog_title))
                .setMessage(getString(R.string.connect_dialog_message))
                .setPositiveButton("Host", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        wifiAwareHelper.attach(true);
                    }
                })
                .setNeutralButton("Client", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        wifiAwareHelper.attach(false);
                    }
                })
                .setCancelable(true)
                .show();
    }

}
