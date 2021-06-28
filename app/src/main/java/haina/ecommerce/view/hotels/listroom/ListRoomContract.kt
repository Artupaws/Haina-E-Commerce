package haina.ecommerce.view.hotels.listroom

import haina.ecommerce.base.BasePresenter
import haina.ecommerce.base.BaseView
import haina.ecommerce.model.hotels.newHotel.DataPricePolicy

interface ListRoomContract {

    interface View:BaseView{
        fun messageGetPricePolicy(msg:String)
        fun getPricePolicy(data:DataPricePolicy?)
    }

    interface Presenter:ListRoomContract, BasePresenter{
        fun getPricePolicy(room_id:String, breakfastStatus:String)
    }


}