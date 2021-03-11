package haina.ecommerce.view.internet

import haina.ecommerce.model.DataUser

interface InternetContract {

    fun messageGetDataUser(msg:String)
    fun getDataUser(data:DataUser?)

}