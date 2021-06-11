package haina.ecommerce.view.flight.schedule

import haina.ecommerce.model.flight.DataAirline
import haina.ecommerce.model.flight.DataAirport

interface ScheduleContract {

    fun messageGetAirport(msg:String)
    fun getDataAirport(data:List<DataAirport?>?)

}