package haina.ecommerce.fcm

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import haina.ecommerce.preference.SharedPreference

class NotifInstanceService():FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("token", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result.toString()
            val sharedPreference = getSharedPreferences("preference", Context.MODE_PRIVATE)
            val editor = sharedPreference.edit()
            editor.putString("token_firebase", token)
            editor.apply()
            // Log and toast
            Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
            Log.d("pesan", token)

            //get preference
//            preference.getValueString("device_token")
            //clear preference
//            preference.clearSharedPreference()
        })
    }

}