package haina.ecommerce.view.hotels.dashboardhotel

import haina.ecommerce.model.hotels.DataItem

interface HotelContract {

    fun getMessageHotel(msg:String)
    fun getDataAllHotel(data:List<DataItem?>?)

}