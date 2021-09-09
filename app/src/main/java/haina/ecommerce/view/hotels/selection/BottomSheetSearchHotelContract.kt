package haina.ecommerce.view.hotels.selection

import haina.ecommerce.base.BaseView
import haina.ecommerce.model.hotels.HotelSearchItem
import haina.ecommerce.model.hotels.newHotel.DataCities
import haina.ecommerce.model.hotels.newHotel.DataHotelDarma

interface BottomSheetSearchHotelContract {

    interface View:BaseView{
        fun getSearch(data:List<HotelSearchItem?>)
    }

}