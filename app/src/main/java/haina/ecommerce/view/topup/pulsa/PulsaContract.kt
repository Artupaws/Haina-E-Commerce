package haina.ecommerce.view.topup.pulsa

import haina.ecommerce.model.pulsaanddata.ProductPhone
import haina.ecommerce.model.pulsaanddata.PulsaItem

interface PulsaContract {

    fun messageCheckProviderAndProduct(msg:String)
    fun getProductPhone(data:ProductPhone?)
}