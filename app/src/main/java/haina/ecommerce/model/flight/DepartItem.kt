package haina.ecommerce.model.flight

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DepartItem(

	@field:SerializedName("depart_time")
	val departTime: String? = null,

	@field:SerializedName("arrival_time")
	val arrivalTime: String? = null,

	@field:SerializedName("airline_code")
	val airlineCode: String? = null,

	@field:SerializedName("price")
	val price: Int? = null,

	@field:SerializedName("airline_detail")
	val airlineDetail: AirlineDetail? = null,

	@field:SerializedName("origin")
	val origin: String? = null,

	@field:SerializedName("destination")
	val destination: String? = null,

	@field:SerializedName("journey_references")
	val journeyReferences: String? = null,

	@field:SerializedName("flight_detail")
	val flightDetail: List<FlightDetailItem?>? = null,

	@field:SerializedName("flight_time")
	val flightTime: List<TimeFlight?>? = null
) : Parcelable