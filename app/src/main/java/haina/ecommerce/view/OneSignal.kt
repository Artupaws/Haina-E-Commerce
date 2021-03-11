package haina.ecommerce.view

import android.app.Application
import com.onesignal.OSInAppMessageAction
import com.onesignal.OSNotificationOpenedResult
import com.onesignal.OneSignal
import com.onesignal.OneSignal.setNotificationOpenedHandler
import haina.ecommerce.util.Constants

class OneSignal: Application() {
    override fun onCreate() {
        super.onCreate()

        // Logging set to help debug issues, remove before releasing your app.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)

        // OneSignal Initialization
        OneSignal.initWithContext(this)
        OneSignal.setAppId(Constants.ONE_SIGNAL_APP_ID)

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
}