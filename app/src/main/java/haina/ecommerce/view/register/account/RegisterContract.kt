package haina.ecommerce.view.register.account

interface RegisterContract {

    fun successCreateUser(msg: String)
    fun errorCreateUser(msg: String)
    fun getTokenUser(token: String)

}