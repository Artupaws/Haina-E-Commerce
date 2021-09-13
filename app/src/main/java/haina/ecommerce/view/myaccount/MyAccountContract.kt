package haina.ecommerce.view.myaccount

import haina.ecommerce.base.BaseView
import haina.ecommerce.model.DataUser

interface MyAccountContract {

    interface View:BaseView{
    fun messageGetDataUser(msg:String)
    fun getDataUser(data : DataUser?)
    fun messageLogout(msg:String)
    fun resetTokenUser(data: String?)
    fun messageChangeImageProfile(msg:String)
    fun messageCheckDataCompany(msg:String)
    }

}