package haina.ecommerce.view

import android.app.Application
import com.onesignal.OneSignal
import haina.ecommerce.util.Constants

class OneSignal: Application() {
    override fun onCreate() {
        super.onCreate()

        // Logging set to help debug issues, remove before releasing your app.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)

        // OneSignal Initialization
        OneSignal.initWithContext(this)
        OneSignal.setAppId(Constants.ONE_SIGNAL_APP_ID)
    }
}