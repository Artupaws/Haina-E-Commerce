package haina.ecommerce.view.hotels.listhotel

import haina.ecommerce.base.BaseView
import haina.ecommerce.model.hotels.newHotel.DataRoom

interface ListHotelContract {

    interface View:BaseView{
        fun messageGetRoomHotel(msg:String)
        fun getDataRoom(data:DataRoom?)
    }
}