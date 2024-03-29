package haina.ecommerce.view.hotels.listcity

import haina.ecommerce.base.BaseView
import haina.ecommerce.model.hotels.newHotel.DataCities
import haina.ecommerce.model.hotels.newHotel.DataHotelDarma

interface ListCityHotelContract {

    interface View:BaseView{
        fun messageGetListCity(msg:String)
        fun messageGetHotelDarma(msg:String)
        fun messageGetListTransactionHotel(msg:String)
        fun getListCity(data:List<DataCities?>?)
        fun getHotelDarma(data: DataHotelDarma?)
        fun getSizeListUnfinish(size:Int?)
    }

}