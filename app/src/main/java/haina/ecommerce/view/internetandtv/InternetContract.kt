package haina.ecommerce.view.internetandtv

import haina.ecommerce.model.productservice.DataProductService

interface InternetContract {

    fun messageGetProductService(msg:String)
    fun getDataProductService(data:List<DataProductService?>?)

}