package haina.ecommerce.broadcastcountdown

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants

class BroadcastService:Service() {

    private val TAG = "BroadcastService"
    val COUNTDOWN_BR = "haina.ecommerce.broadcastcountdown"
    var intent = Intent(COUNTDOWN_BR)
    var countDownTimer: CountDownTimer? = null
    var sharedPreferences: SharedPreferences? = null
    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "Starting timer...")
        sharedPreferences = getSharedPreferences(packageName, Context.MODE_PRIVATE)
        var millis = sharedPreferences!!.getLong("time", 0)
        if (millis / 1000 == 0L) {
            millis = 300000
        }
        countDownTimer = object : CountDownTimer(millis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                Log.i(TAG, "Countdown seconds remaining:" + millisUntilFinished / 1000)
                intent.putExtra("countdown", millisUntilFinished)
                sendBroadcast(intent)
            }
            override fun onFinish() {}
        }
        countDownTimer!!.start()
    }

    override fun onDestroy() {
        countDownTimer!!.cancel()
        super.onDestroy()
    }


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

}