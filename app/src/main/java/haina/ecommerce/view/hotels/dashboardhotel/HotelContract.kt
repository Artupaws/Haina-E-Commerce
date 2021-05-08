package haina.ecommerce.view.hotels.dashboardhotel

import haina.ecommerce.model.hotels.DataItem
import haina.ecommerce.model.hotels.LocationHotels

interface HotelContract {

    fun getMessageHotel(msg:String)
    fun getDataAllHotel(data:List<DataItem?>?)
    fun getListCity(data: MutableList<LocationHotels>?)

}