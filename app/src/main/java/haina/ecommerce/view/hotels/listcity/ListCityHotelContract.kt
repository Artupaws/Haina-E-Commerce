package haina.ecommerce.view.hotels.listcity

import haina.ecommerce.model.hotels.newHotel.DataCities

interface ListCityHotelContract {

    fun messageGetListCity(msg:String)
    fun getListCity(data:List<DataCities?>?)

}