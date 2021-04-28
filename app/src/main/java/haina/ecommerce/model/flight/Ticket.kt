package haina.ecommerce.model.flight

import android.os.Parcelable
import com.vinay.stepview.models.Step
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ticket (val nameAirlines:String, val iconAirline:String,
                   val listFlightTime:List<TimeFlight>?, val flightTime:String, val typeFlight:String,
                   val cityCodeDeparture:String, val cityCodeArrived:String, val priceTicket:String, val departureTime:String, val arrivedTime:String):Parcelable