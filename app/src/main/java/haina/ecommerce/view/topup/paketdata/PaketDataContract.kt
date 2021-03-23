package haina.ecommerce.view.topup.paketdata

import haina.ecommerce.model.pulsaanddata.DataItem
import haina.ecommerce.model.pulsaanddata.PaketDataItem
import haina.ecommerce.model.pulsaanddata.ProductPhone

interface PaketDataContract {

    fun messageCheckProviderAndProduct(msg:String)
    fun getProductPhone(data: ProductPhone?)
}