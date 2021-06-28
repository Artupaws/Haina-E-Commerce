package haina.ecommerce.view.hotels.listhotel

import haina.ecommerce.model.hotels.newHotel.DataRoom

interface ListHotelContract {

    fun messageGetRoomHotel(msg:String)
    fun getDataRoom(data:DataRoom?)

}