package haina.ecommerce.view

import android.app.Application
import com.onesignal.*
import com.onesignal.OneSignal
import haina.ecommerce.util.Constants


class OneSignal: Application() {
//    override fun onCreate() {
//        super.onCreate()
//
//        // Logging set to help debug issues, remove before releasing your app.
//        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
//
//        // OneSignal Initialization
//        OneSignal.setAppId(Constants.ONE_SIGNAL_APP_ID)
//        OneSignal.addSubscriptionObserver(this)

//        android.util.Log.d("playerId", playerId.toString())

//        OneSignal.setInAppMessageClickHandler(
//             OneSignal.OSInAppMessageClickHandler() {
//                @Override
//                fun inAppMessageClicked(OSInAppMessageAction result) {
//                    val clickName = result.getClickName();
//                    val clickUrl = result.getClickUrl();
//
//                    val closesMessage = result.doesCloseMessage();
//                    val firstClick = result.isFirstClick();
//                }
//            })
    }

//    override fun onOSSubscriptionChanged(stateChanges: OSSubscriptionStateChanges?) {
//        TODO("Not yet implemented")
//    }

//    interface AttributeOneSignal{
//        fun getPlayerId(): String {
//
//            return device?.userId.toString()
//        }
//    }

//    override fun onOSSubscriptionChanged(stateChanges: OSSubscriptionStateChanges) {
//        if (!stateChanges.from.getSubscribed() &&
//            stateChanges.to.getSubscribed()
//        ) {
//            // The user is subscribed
//            // Either the user subscribed for the first time
//            // Or the user was subscribed -> unsubscribed -> subscribed
//            stateChanges.to.userId
//            // Make a POST call to your server with the user ID
//        }
//    }

//    override fun onOSSubscriptionChanged(stateChanges: OSSubscriptionStateChanges?) {
//        if (!stateChanges?.from?.userId &&
//            stateChanges?.to?.userId) {
//            // The user is subscribed
//            // Either the user subscribed for the first time
//            // Or the user was subscribed -> unsubscribed -> subscribed
//            stateChanges?.to?.userId;
//            // Make a POST call to your server with the user ID
//        }
//    }
//}
