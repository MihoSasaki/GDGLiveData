package com.example.sasakimiho.gdglivedata;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.util.Calendar;
import java.util.Date;

public class ClockLegacy {
    private final Context mAppContext;

    public interface ClockListener {
        void onReceive(Date date);
    }

    private ClockListener mListener;
//    https://gist.github.com/egglang/ae119ac753b901bbd2301dc323fd7402
    private BroadcastReceiver mTimeTickBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (mListener != null) {
                mListener.onReceive(Calendar.getInstance().getTime());
            }
        }
    };

    public ClockLegacy(Context appContext) {
        mAppContext = appContext;
    }

    public void setClockListener(ClockListener listener) {
        if (mListener != null) {
            return;
        }
        mListener = listener;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_TIME_TICK);
        mAppContext.registerReceiver(mTimeTickBroadcastReceiver, intentFilter);
    }

    public void removeClockListener() {
        mListener = null;
        mAppContext.unregisterReceiver(mTimeTickBroadcastReceiver);
    }
}
