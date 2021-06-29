package haina.ecommerce.view.login

import haina.ecommerce.base.BaseView

interface LoginContract {

    interface View:BaseView {
        fun successLogin(msg: String)
        fun failedLogin(msg: String)
        fun getToken(token: String)
        fun loginRegistration()
    }
}