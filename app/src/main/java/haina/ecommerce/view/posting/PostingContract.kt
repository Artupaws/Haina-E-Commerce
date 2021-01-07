package haina.ecommerce.view.posting

import haina.ecommerce.model.DataCompany
import haina.ecommerce.model.DataMyJob

interface PostingContract {

    fun checkRegisterCompanyTrue(msg:String, item:DataCompany?)
    fun checkRegisterCompanyFalse(msg:String)

}