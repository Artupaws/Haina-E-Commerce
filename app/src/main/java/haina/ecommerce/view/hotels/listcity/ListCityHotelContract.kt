package haina.ecommerce.view.hotels.listcity

import haina.ecommerce.model.hotels.newHotel.DataCities
import haina.ecommerce.model.hotels.newHotel.DataHotelDarma

interface ListCityHotelContract {

    fun messageGetListCity(msg:String)
    fun messageGetHotelDarma(msg:String)
    fun getListCity(data:List<DataCities?>?)
    fun getHotelDarma(data: DataHotelDarma?)

}