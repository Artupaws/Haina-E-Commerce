package haina.ecommerce.model.flight

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataAirport(

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("iata")
	val iata: String? = null,

	@field:SerializedName("dst")
	val dst: String? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("timezone")
	val timezone: Int? = null,

	@field:SerializedName("timezone_olson")
	val timezoneOlson: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("alt")
	val alt: Int? = null,

	@field:SerializedName("icao")
	val icao: String? = null,

	@field:SerializedName("lon")
	val lon: Double? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("lat")
	val lat: Double? = null
) : Parcelable