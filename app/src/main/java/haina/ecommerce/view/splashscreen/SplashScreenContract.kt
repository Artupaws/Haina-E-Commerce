package haina.ecommerce.view.splashscreen

import haina.ecommerce.base.BaseView

interface SplashScreenContract {

    interface View: BaseView {
        fun serverOnline(msg:String)
        fun serverOffline(msg:String)

    }
}