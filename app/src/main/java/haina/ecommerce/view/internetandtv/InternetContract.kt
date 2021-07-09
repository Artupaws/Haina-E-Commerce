package haina.ecommerce.view.internetandtv

import haina.ecommerce.model.bill.DataBill
import haina.ecommerce.model.bill.DataInquiry
import haina.ecommerce.model.bill.DataNoInquiry
import haina.ecommerce.model.productservice.DataProductService

interface InternetContract {

    fun messageGetProductService(msg:String)
    fun getDataProductService(data:List<DataProductService?>?)
    fun messageGetBillAmount(msg:String)
    fun messageGetBillDirect(msg:String)
    fun getDataBillAmount(data: DataInquiry)
    fun getDataBillDirect(data: DataNoInquiry)

}