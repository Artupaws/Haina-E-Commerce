package haina.ecommerce.view.hotels.dashboardhotel

import haina.ecommerce.model.hotels.DataHotel
import haina.ecommerce.model.hotels.LocationHotels

interface HotelContract {

    fun getMessageHotel(msg:String)
    fun getDataAllHotel(data:List<DataHotel?>?)
    fun getListCity(data: MutableList<LocationHotels>?)

}