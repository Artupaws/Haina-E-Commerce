package haina.ecommerce.view.hotels.listroom

import haina.ecommerce.base.BaseView
import haina.ecommerce.model.hotels.newHotel.DataPricePolicy

interface ListRoomContract {

    interface View:BaseView{
        fun messageGetPricePolicy(msg:String)
        fun getPricePolicy(data:DataPricePolicy?)
    }

}