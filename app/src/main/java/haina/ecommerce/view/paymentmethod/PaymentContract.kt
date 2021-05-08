package haina.ecommerce.view.paymentmethod

import haina.ecommerce.model.paymentmethod.DataPaymentMethod

interface PaymentContract {

    fun messageGetPaymentMethod(msg:String)
    fun messageCreateTransaction(msg:String)
    fun getDataPaymentMethod(data:List<DataPaymentMethod?>?)
    fun messageBookingHotel(msg: String)

}