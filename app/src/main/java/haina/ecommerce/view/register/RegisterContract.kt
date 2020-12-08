package haina.ecommerce.view.register

import haina.ecommerce.model.Data

interface RegisterContract {

    fun successCreateUser(msg: String)
    fun errorCreateUser(msg: String)
    fun getTokenUser(token: String)

}