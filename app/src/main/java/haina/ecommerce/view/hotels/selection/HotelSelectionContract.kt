package haina.ecommerce.view.hotels.selection

import haina.ecommerce.base.BaseView
import haina.ecommerce.model.hotels.HotelSearchItem
import haina.ecommerce.model.hotels.newHotel.DataCities
import haina.ecommerce.model.hotels.newHotel.DataHotelDarma

interface HotelSelectionContract {

    interface View:BaseView{
        fun messageGetListCity(msg:String)
        fun messageGetHotelDarma(msg:String)
        fun messageGetListTransactionHotel(msg:String)
        fun getListCity(data:List<DataCities?>?)
        fun getHotelDarma(data: DataHotelDarma?)
        fun getSizeListUnfinish(size:Int?)
        fun getSearch(data:List<HotelSearchItem?>)
    }

}