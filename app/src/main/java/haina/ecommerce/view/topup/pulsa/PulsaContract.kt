package haina.ecommerce.view.topup.pulsa

import haina.ecommerce.base.BaseView
import haina.ecommerce.model.pulsaanddata.ProductPhone
import haina.ecommerce.model.pulsaanddata.PulsaItem

interface PulsaContract {

    interface View:BaseView{
        fun messageCheckProviderAndProduct(msg:String)
        fun getProductPhone(data:ProductPhone?)
    }
}