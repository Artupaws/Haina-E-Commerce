package haina.ecommerce.view.payment

import haina.ecommerce.model.paymentmethod.DataPaymentMethod

interface PaymentContract {

    fun messageGetPaymentMethod(msg:String)
    fun getDataPaymentMethod(data:List<DataPaymentMethod?>?)

}