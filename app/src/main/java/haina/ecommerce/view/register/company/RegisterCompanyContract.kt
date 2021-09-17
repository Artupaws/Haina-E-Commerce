package haina.ecommerce.view.register.company

import haina.ecommerce.model.property.DataProvince

interface RegisterCompanyContract {

    fun messageRegisterCompany(msg:String)
    fun messageProvince(msg:String)
    fun showProvince(data: List<DataProvince?>)

}