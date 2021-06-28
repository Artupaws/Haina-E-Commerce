package haina.ecommerce.view.hotels.listroom

import haina.ecommerce.model.hotels.newHotel.DataPricePolicy

interface ListRoomContract {

    fun messageGetPricePolicy(msg:String)
    fun getPricePolicy(data:DataPricePolicy?)

}