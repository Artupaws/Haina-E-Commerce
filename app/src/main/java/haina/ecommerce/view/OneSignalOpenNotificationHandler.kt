package haina.ecommerce.view

import android.app.Application
import android.content.Intent
import android.util.Log
import com.onesignal.OSNotificationAction.ActionType
import com.onesignal.OSNotificationOpenedResult
import com.onesignal.OneSignal
import haina.ecommerce.view.notification.NotificationActivity
import org.json.JSONObject


class OneSignalOpenNotificationHandler: Application(), OneSignal.OSNotificationOpenedHandler {

    override fun notificationOpened(result: OSNotificationOpenedResult?) {
//        val data: JSONObject = result!!.notification.payload.additionalData
//        if (data != null) {
//            val myCustomData = data.optString("key", null)
//        }
//
//        // React to button pressed
//        val actionType = result.action.type
//        if (actionType == ActionType.ActionTaken) Log.i(
//            "OneSignalExample",
//            "Button pressed with id: " + result.action.actionID
//        )

        startApp()

    }

    private fun startApp() {
        val intent: Intent = Intent(this, NotificationActivity::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or Intent.FLAG_ACTIVITY_NEW_TASK)
        this.startActivity(intent)
    }
}