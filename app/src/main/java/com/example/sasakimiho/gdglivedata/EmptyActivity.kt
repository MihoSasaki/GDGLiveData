package com.example.sasakimiho.gdglivedata

import android.arch.lifecycle.*
import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import java.util.*
import com.example.sasakimiho.gdglivedata.databinding.ActivityEmptyBinding

class EmptyActivity : AppCompatActivity() {
    val clockLiveData = ClockLiveData(this)

    val clockViewModel: ClockViewModel by lazy {
        ViewModelProviders.of(this).get(ClockViewModel::class.java)
    }

    val clockObserver: Observer<Date> = Observer<Date> { date ->
        date?.let {
            clockText.text = getTimeString(it)
        }
    }

    val clockStringObserver: Observer<String> = Observer<String> { dateString ->
        dateString?.let {
            clockText.text = it
        }
    }

    fun getTimeDate(): Date {
        return Calendar.getInstance().time
    }

    val simpleObserver: GenericLifecycleObserver = GenericLifecycleObserver { _, event ->
        Log.e("sasamiiho", "get" + event.name)
    }

    final val observer: LifecycleObserver = object : LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        fun onStartEvent() {
//            clockObserver.onChanged(getTimeDate())
//            clockViewModel.clock?.postValue(getTimeDate())
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        fun onStopEvent() {
        }

    }

    //    val myObserver: LifecycleObserver = MyGenericLifecycle()
    val clockText: TextView by lazy {
        findViewById<TextView>(R.id.clock) as TextView
    }
    val clockLegacy: ClockLegacy = ClockLegacy(this)

    val clockListener = ClockLegacy.ClockListener() { date ->
        val cal = Calendar.getInstance()
        cal.time = date
        val dateString = String.format("%02d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE))
        clockText.text = dateString
    }

//    val currentClock: LiveData<String> = Transformations.map(clockViewModel.getClock(), { input: Date? -> input?.let { getTimeString(input) } })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empty)
        val binding = DataBindingUtil.setContentView<ActivityEmptyBinding>(this, R.layout.activity_empty)
//        currentClock.observe(this, clockStringObserver)
//        binding.setLifecycleOwner(this);
        binding.clockViewModel = clockViewModel
        lifecycle.addObserver(simpleObserver)
//        clockLiveData.observe(this, clockObserver)
//        clockViewModel.getClock().observe(this, clockObserver)
    }

    fun getTimeString(date: Date): String {
        val cal = Calendar.getInstance()
        cal.time = date
        val dateString = cal.get(Calendar.HOUR_OF_DAY).toString() + " : " + cal.get(Calendar.MINUTE).toString()
        return dateString
    }

}

class MyGenericLifecycle : GenericLifecycleObserver {

    override fun onStateChanged(source: LifecycleOwner?, event: Lifecycle.Event?) {

        Log.e("state changed", "called")
        Log.e("generic lifecycle", "name" + source?.lifecycle?.currentState?.name + "   " + event?.name)
    }
}
