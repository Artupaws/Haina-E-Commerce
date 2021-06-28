package haina.ecommerce.view.hotels.listhotel

import haina.ecommerce.base.BasePresenter
import haina.ecommerce.base.BaseView
import haina.ecommerce.model.hotels.newHotel.DataRoom
import haina.ecommerce.view.hotels.listcity.ListCityHotelContract

interface ListHotelContract {

    interface View: BaseView {
        fun messageGetRoomHotel(msg: String)
        fun getDataRoom(data: DataRoom?)
    }

    interface Presenter : ListCityHotelContract, BasePresenter {
        fun getDataRoom(idHotel:String)
    }
}