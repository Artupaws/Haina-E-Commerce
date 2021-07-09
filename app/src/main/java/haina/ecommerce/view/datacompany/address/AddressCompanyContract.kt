package haina.ecommerce.view.datacompany.address

import haina.ecommerce.model.DataItemHaina

interface AddressCompanyContract {

    fun getListLocation(list:List<DataItemHaina?>?)
    fun messageAddressCompany(msg:String)
    fun messageGetListLocation(msg:String)

}