package haina.ecommerce.view.other

import haina.ecommerce.model.service.DataService

interface OtherContract {

    fun messageGetListService(msg:String)
    fun getListService(data:List<DataService?>?)

}