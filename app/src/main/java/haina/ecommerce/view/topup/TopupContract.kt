package haina.ecommerce.view.topup

import haina.ecommerce.base.BaseView
import haina.ecommerce.model.DataUser
import haina.ecommerce.model.pulsaanddata.ProductPhone

interface TopupContract {

    interface View:BaseView{
        fun messageGetDataUser(msg:String)
        fun messageGetProviderName(msg:String)
        fun getDataUser(data:DataUser?)
        fun getProviderName(data:ProductPhone?)
    }
}