package haina.ecommerce.view.paymentmethod

import haina.ecommerce.base.BaseView
import haina.ecommerce.model.paymentmethod.DataPaymentMethod

interface PaymentContract {

    interface View:BaseView{
        fun messageGetPaymentMethod(msg:String)
        fun messageCreateTransaction(msg:String)
        fun messageCreateBillTransaction(msg:String)
        fun getDataPaymentMethod(data:List<DataPaymentMethod?>?)
        fun messageBookingHotel(msg: String)
        fun messageCreateVacancy(msg:String)
    }
}