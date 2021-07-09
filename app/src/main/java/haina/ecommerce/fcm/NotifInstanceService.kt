package haina.ecommerce.fcm

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import haina.ecommerce.R
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import haina.ecommerce.view.splashscreen.SplashScreenActivity


class NotifInstanceService() : FirebaseMessagingService() {

    lateinit var sharedPrefHelper : SharedPreferenceHelper
    var TAG = "FIREBASE MESSAGING"

    override fun onNewToken(token: String) {
        sharedPrefHelper = SharedPreferenceHelper(this)
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("token", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            // Get new FCM registration token
            val msg = task.result
            msg?.let { sharedPrefHelper.save(Constants.PREF_TOKEN_FIREBASE, it) }
            Log.d("tokenFirebase",msg!!)
            // Log and toast
        })
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.data.isNotEmpty()){
            sendNotification(remoteMessage)
        }
    }

    @SuppressLint("WrongConstant")
    private fun sendNotification(remoteMessage: RemoteMessage) {
        val intent = Intent(this, SplashScreenActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
            PendingIntent.FLAG_ONE_SHOT)

        val data :Map<String, String> = remoteMessage.data
        val page: String? = data["page"]
        Log.d("messageFirebaseNotif", page.toString())
        val title :String = remoteMessage.notification?.title.toString()
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notifChannelId = "HainaApp123"
        val NOTIFICATION_CHANNEL_ID = "NOTIFICATION_CHANNEL_ID"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel :NotificationChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, "Haina Notif", NotificationManager.IMPORTANCE_MAX)
            notificationChannel.description = "Description"
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val notificationBuilder : NotificationCompat.Builder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
        notificationBuilder.setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_ALL)
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.drawable.app_logo)
            .setTicker("Hearty365")
            .setContentTitle(title)
            .setContentInfo("info")
            .setContentIntent(pendingIntent)

        notificationManager.notify(1, notificationBuilder.build())


    }

}