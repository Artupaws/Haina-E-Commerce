package haina.ecommerce.view.checkout

import haina.ecommerce.model.bill.DataBill

interface CheckoutContract {

    fun messageCheckout(msg:String)
    fun messageGetBillAmount(msg:String)
    fun getDataBillAmount(data: DataBill)
}