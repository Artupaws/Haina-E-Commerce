package haina.ecommerce.view.topup

import haina.ecommerce.model.DataUser

interface TopupContract {

    fun messageGetDataUser(msg:String)
    fun getDataUser(data:DataUser?)

}