package haina.ecommerce.view.hotels.listcity

import haina.ecommerce.base.BasePresenter
import haina.ecommerce.base.BaseView
import haina.ecommerce.model.hotels.newHotel.DataCities
import haina.ecommerce.model.hotels.newHotel.DataHotelDarma

interface ListCityHotelContract {

    interface View: BaseView {
        fun getListCity(data:List<DataCities?>?)
        fun messageGetListCity(msg:String)
        fun messageGetHotelDarma(msg:String)
        fun getListHotelDarma(data: DataHotelDarma?)

    }

    interface Presenter : ListCityHotelContract, BasePresenter {
        fun getListCity()
        fun getListHotelDarma(countryId:String, cityId:Int, paxPassport:String, checkIn:String, checkOut:String)
    }

//    fun messageGetListCity(msg:String)
//    fun messageGetHotelDarma(msg:String)
//    fun getListCity(data:List<DataCities?>?)
//    fun getHotelDarma(data: DataHotelDarma?)

}