package haina.ecommerce.model.flight

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FlightDetailItem(


	@field:SerializedName("available_detail")
	val availableDetail: List<AvailableDetailItem?>? = null,

	@field:SerializedName("airlineCode")
	val airlineCode: String? = null,

	@field:SerializedName("fdOrigin")
	val fdOrigin: String? = null,

	@field:SerializedName("fdDestination")
	val fdDestination: String? = null,

	@field:SerializedName("fdArrivalTime")
	val fdArrivalTime: String? = null,

	@field:SerializedName("fdDepartTime")
	val fdDepartTime: String? = null,

	@field:SerializedName("routeInfo")
	val routeInfo: String? = null,

	@field:SerializedName("flightNumber")
	val flightNumber: String? = null
) : Parcelable