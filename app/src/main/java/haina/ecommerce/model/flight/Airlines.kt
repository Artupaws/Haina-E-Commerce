package haina.ecommerce.model.flight

import com.vinay.stepview.models.Step

data class Airlines (val nameAirlines:String, val iconAirline:String,
val listFlightTime:List<TimeFlight>?, val flightTime:String, val typeFlight:String,
val cityCodeDeparture:String, val cityCodeArrived:String, val priceTicket:String, val departureTime:String, val arrivedTime:String)