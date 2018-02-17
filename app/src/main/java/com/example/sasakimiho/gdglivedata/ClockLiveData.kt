package com.example.sasakimiho.gdglivedata

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import java.util.*

public class ClockLiveData(val context: Context) : MutableLiveData<Date>() {

    val clockBloadCastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            Log.e("sasami", "get value!!")
            postValue(Calendar.getInstance().time)
        }
    }

    override fun onActive() {
        super.onActive()
        val intentFilter = IntentFilter()
        intentFilter.addAction(Intent.ACTION_TIME_TICK)
        context.registerReceiver(clockBloadCastReceiver, intentFilter)
    }

    override fun onInactive() {
        super.onInactive()
        context.unregisterReceiver(clockBloadCastReceiver)
    }

    fun getTimeDate(): Date {
        return Calendar.getInstance().time
    }
}