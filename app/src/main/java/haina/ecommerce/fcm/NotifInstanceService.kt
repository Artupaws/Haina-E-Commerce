package haina.ecommerce.fcm

import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants

class NotifInstanceService() : FirebaseMessagingService() {

    lateinit var sharedPrefHelper : SharedPreferenceHelper

    override fun onNewToken(token: String) {
        sharedPrefHelper = SharedPreferenceHelper(this)
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("token", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            // Get new FCM registration token
            val token = task.result.toString()
            sharedPrefHelper.save(Constants.PREF_TOKEN_FIREBASE, token)
            // Log and toast
            Log.d("pesan", token)
        })
    }

}