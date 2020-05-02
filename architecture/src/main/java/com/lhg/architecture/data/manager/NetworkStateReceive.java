package com.lhg.architecture.data.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

import com.lhg.architecture.R;
import com.lhg.architecture.utils.NetworkUtils;

import java.util.Objects;

/**
 * Create by KunMinX at 19/8/5
 */
public class NetworkStateReceive extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Objects.equals(intent.getAction(), ConnectivityManager.CONNECTIVITY_ACTION)) {
            if (!NetworkUtils.isConnected()) {
                Toast.makeText(context, context.getString(R.string.network_not_good), Toast.LENGTH_SHORT).show();
            }
        }
    }

}
