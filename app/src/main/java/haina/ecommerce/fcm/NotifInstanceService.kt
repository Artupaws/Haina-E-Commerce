package haina.ecommerce.fcm

import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import haina.ecommerce.preference.SharedPreference

class NotifInstanceService:FirebaseMessagingService() {

//    private val preference: SharedPreference= SharedPreference(applicationContext)

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("token", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result.toString()
            // Log and toast
            Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
//            preference.save("device_token", token)
            Log.d("pesan", token)

            //get preference
//            preference.getValueString("device_token")
            //clear preference
//            preference.clearSharedPreference()
        })
    }

}