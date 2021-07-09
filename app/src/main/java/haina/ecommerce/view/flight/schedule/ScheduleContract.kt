package haina.ecommerce.view.flight.schedule

import haina.ecommerce.base.BaseView
import haina.ecommerce.model.flight.DataAirline
import haina.ecommerce.model.flight.DataAirport

interface ScheduleContract {

    interface View:BaseView{
        fun messageGetAirport(msg:String)
        fun getDataAirport(data:List<DataAirport?>?)
    }
}