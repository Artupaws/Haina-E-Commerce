package haina.ecommerce.view.paymentmethod

import haina.ecommerce.base.BasePresenter
import haina.ecommerce.base.BaseView
import haina.ecommerce.model.hotels.newHotel.RequestBookingHotelToDarma
import haina.ecommerce.model.paymentmethod.DataPaymentMethod

interface PaymentContract {

    interface View:BaseView{
        fun messageGetPaymentMethod(msg:String)
        fun messageCreateTransaction(msg:String)
        fun messageCreateBillTransaction(msg:String)
        fun getDataPaymentMethod(data:List<DataPaymentMethod?>?)
        fun messageBookingHotel(msg: String)
    }

    interface Presenter:PaymentContract, BasePresenter{
        fun getPaymentMethod()
        fun createTransactionPhone(customerNumber:String, productCode:String, idPaymentMethod:Int, idInquiry: Int)
        fun createTransactionBill(productCode:String, amount:String, customerNumber:String, idPaymentMethod:Int, idInquiry:Int?)
        fun createBookingHotelDarma(dataBooking: RequestBookingHotelToDarma)
    }



}