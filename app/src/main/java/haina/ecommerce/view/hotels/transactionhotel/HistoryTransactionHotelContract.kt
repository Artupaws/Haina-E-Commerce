package haina.ecommerce.view.hotels.transactionhotel

import haina.ecommerce.base.BaseView
import haina.ecommerce.model.hotels.newHotel.DataBooking
import haina.ecommerce.model.hotels.transactionhotel.DataTransactionHotel

interface HistoryTransactionHotelContract {

    interface View:BaseView{
        fun messageGetListTransactionHotel(msg:String)
        fun getListTransactionHotel(dataHotel: DataTransactionHotel?)
        fun getListBookingHotelDarma(dataHotel: DataBooking?)
        fun messageCancelBookingHotel(msg:String)
    }

}