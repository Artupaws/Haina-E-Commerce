package haina.ecommerce.view.checkout

import haina.ecommerce.model.bill.DataBill
import haina.ecommerce.model.bill.DataInquiry
import haina.ecommerce.model.checkout.DataCheckout

interface CheckoutContract {

    fun messageCheckout(msg:String)
    fun getDataCheckoutTopup(data:DataCheckout?)
}