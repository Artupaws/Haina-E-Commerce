package haina.ecommerce.view.topup.paketdata

import haina.ecommerce.model.pulsaanddata.PaketDataItem

interface PaketDataContract {

    fun messageCheckProviderAndProduct(msg:String)
    fun getProductPhone(data: List<PaketDataItem?>?)
}