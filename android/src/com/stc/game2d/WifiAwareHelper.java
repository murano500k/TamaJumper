package com.stc.game2d;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.net.NetworkSpecifier;
import android.net.wifi.aware.AttachCallback;
import android.net.wifi.aware.DiscoverySession;
import android.net.wifi.aware.DiscoverySessionCallback;
import android.net.wifi.aware.PeerHandle;
import android.net.wifi.aware.PublishConfig;
import android.net.wifi.aware.PublishDiscoverySession;
import android.net.wifi.aware.SubscribeConfig;
import android.net.wifi.aware.SubscribeDiscoverySession;
import android.net.wifi.aware.WifiAwareManager;
import android.net.wifi.aware.WifiAwareSession;
import android.util.Log;

import java.util.List;

/**
 * Created by artem on 1/5/18.
 */

public class WifiAwareHelper extends AttachCallback {
    private static final String TAG = "WifiAwareHelper";
    private ConnectivityManager connectivityManager;
    WifiAwareManager wifiAwareManager;
    WifiAwareSession wifiAwareSession;
    DiscoverySession discoverySession;
    public PeerHandle handle;
    private ConnectivityManager.NetworkCallback mCallback;
    private Context context;
    private Network mNetwork;
    private boolean isHost;

    public WifiAwareHelper(Context context) {
        this.context=context;
        this.wifiAwareManager = (WifiAwareManager) context.getSystemService(Context.WIFI_AWARE_SERVICE);
        this.connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }
    public void attach(boolean isHost){
        Log.d(TAG, "attach() called with: isHost = [" + isHost + "]");
        this.isHost=isHost;
        wifiAwareManager.attach(this, null);
    }



    public void createConnection(PeerHandle handle){
        Log.d(TAG, "createConnection() called with: handle = [" + handle + "]");
        NetworkSpecifier networkSpecifier = discoverySession.createNetworkSpecifierOpen(handle);
        NetworkRequest myNetworkRequest = new NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI_AWARE)
                .setNetworkSpecifier(networkSpecifier)
                .build();
        mCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(Network network) {
                Log.d(TAG, "onAvailable() called with: network = [" + network + "]");
                mNetwork=network;
            }
            @Override
            public void onLinkPropertiesChanged(Network network,
                                                LinkProperties linkProperties) {
                Log.d(TAG, "onLinkPropertiesChanged() called with: network = [" + network + "], linkProperties = [" + linkProperties + "]");
            }
            @Override
            public void onLost(Network network) {
                Log.d(TAG, "onLost() called with: network = [" + network + "]");
                mNetwork=null;
            }
        };
        connectivityManager.requestNetwork(myNetworkRequest, mCallback);
    }

    @Override
    public void onAttached(WifiAwareSession session) {
        Log.d(TAG, "onAttached() called with: session = [" + session + "] isHost="+isHost);
        wifiAwareSession=session;
        if(isHost) initHost();
        else initClient();
    }

    public void initHost(){
        PublishConfig config = new PublishConfig.Builder()
                .setServiceName(Config.AWARE_SERVICE_NAME)
                .build();

        wifiAwareSession.publish(config, new DiscoverySessionCallback() {
            @Override
            public void onPublishStarted(PublishDiscoverySession session) {
                discoverySession=session;
                Log.d(TAG, "onPublishStarted() called with: session = [" + session + "]");
            }

            @Override
            public void onMessageReceived(PeerHandle peerHandle, byte[] message) {
                Log.d(TAG, "onMessageReceived() called with: peerHandle = [" + peerHandle + "], message = [" + message + "]");
                createConnection(peerHandle);
            }
        }, null);
    }

    public void initClient(){
        SubscribeConfig config = new SubscribeConfig.Builder()
                .setServiceName(Config.AWARE_SERVICE_NAME)
                .build();

        wifiAwareSession.subscribe(config, new DiscoverySessionCallback() {
            @Override
            public void onSubscribeStarted(SubscribeDiscoverySession session) {
                discoverySession=session;
                Log.d(TAG, "onSubscribeStarted() called with: session = [" + session + "]");
            }

            @Override
            public void onServiceDiscovered(PeerHandle peerHandle,
                                            byte[] serviceSpecificInfo, List<byte[]> matchFilter) {
                Log.d(TAG, "onServiceDiscovered() called with: peerHandle = [" + peerHandle + "], serviceSpecificInfo = [" + serviceSpecificInfo + "], matchFilter = [" + matchFilter + "]");

                discoverySession.sendMessage(peerHandle, 0,null);
            }
        }, null);
    }
}
