package haina.ecommerce.view.datacompany

import haina.ecommerce.model.DataCompany
import haina.ecommerce.model.DataItemHaina
import haina.ecommerce.model.PhotoItemDataCompany

interface DataCompanyContract {

    fun getDataCompany(item:DataCompany)
    fun loadJobLocation(itemHaina: List<DataItemHaina?>?)
    fun messageGetDataCompany(msg:String)
    fun messageAddPhotoCompany(msg: String)
    fun messageDeleteImageCompany(msg: String)

}