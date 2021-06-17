package haina.ecommerce.view.login

interface LoginContract {

    fun successLogin(msg: String)
    fun failedLogin(msg: String)
    fun getToken(token: String)
    fun loginRegistration()

}