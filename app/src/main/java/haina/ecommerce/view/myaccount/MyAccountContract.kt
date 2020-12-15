package haina.ecommerce.view.myaccount

import haina.ecommerce.model.DataUser

interface MyAccountContract {

    fun successGetDataUser(msg:String)
    fun errorGetDataUSer(msg:String)
    fun getDataUser(data : DataUser?)

    fun successLogout(msg:String)
    fun errorLogout(msg:String)
    fun resetTokenUser(data: String?)

    fun successChangeImageProfile(msg:String)
    fun errorChangeImageProfile(msg:String)


}