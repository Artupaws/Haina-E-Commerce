package haina.ecommerce.view.checkout

import haina.ecommerce.base.BaseView
import haina.ecommerce.model.bill.DataBill
import haina.ecommerce.model.bill.DataInquiry
import haina.ecommerce.model.checkout.DataCheckout

interface CheckoutContract {

    interface View:BaseView{
        fun messageCheckout(msg:String)
        fun getDataCheckoutTopup(data:DataCheckout?)
    }
}