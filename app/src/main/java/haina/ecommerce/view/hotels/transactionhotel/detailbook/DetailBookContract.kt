package haina.ecommerce.view.hotels.transactionhotel.detailbook

import haina.ecommerce.base.BaseView

interface DetailBookContract {

    interface View:BaseView{
        fun messageCancelBookingHotel(msg:String)
    }
}