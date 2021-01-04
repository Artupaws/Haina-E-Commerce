package haina.ecommerce.view.myaccount.detailaccount

import haina.ecommerce.model.DataDocumentUser

interface DetailAccountContract {

    fun getDocumentResume(item: List<DataDocumentUser?>?)
    fun getDocumentPortfolio(item: List<DataDocumentUser?>?)
    fun getDocumentCertificate(item: List<DataDocumentUser?>?)
    fun messageLoadDetailUser(msg:String)

}