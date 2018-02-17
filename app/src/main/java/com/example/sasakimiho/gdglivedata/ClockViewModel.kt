package com.example.sasakimiho.gdglivedata

import android.app.Application
import android.arch.core.util.Function
import android.arch.lifecycle.*
import java.text.SimpleDateFormat
import java.util.*

class ClockViewModel(application: Application) : AndroidViewModel(application) {
    var clock: MutableLiveData<Date>? = null
    var stringClock: LiveData<String>

    var clockLiveData: MutableLiveData<Date> = ClockLiveData(application)

    init {
        stringClock = Transformations.map(clockLiveData, Function {
            return@Function SimpleDateFormat("HH:mm", Locale.getDefault()).format(it)
        })
    }

//    fun getClock(): LiveData<Date> {
//        if (clock == null) {
//            clock = ClockLiveData(application)
//        }
//        return clock as MutableLiveData<Date>
//    }

    public fun getFormattedDate(): String {
        return clock?.value?.let {
            getTimeString(it)
        } ?: ""
    }

    fun getTimeString(date: Date): String {
        val cal = Calendar.getInstance()
        cal.time = date
        val dateString = cal.get(Calendar.HOUR_OF_DAY).toString() + " : " + cal.get(Calendar.MINUTE).toString()
        return dateString
    }
}

