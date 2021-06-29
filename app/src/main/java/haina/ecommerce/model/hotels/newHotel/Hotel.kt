package haina.ecommerce.model.hotels.newHotel

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Hotel(

	@field:SerializedName("id_darma")
	val idDarma: String? = null,

	@field:SerializedName("hotel_phone")
	val hotelPhone: String? = null,

	@field:SerializedName("hotel_website")
	val hotelWebsite: String? = null,

	@field:SerializedName("hotel_email")
	val hotelEmail: String? = null,

	@field:SerializedName("hotel_rating")
	val hotelRating: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("hotel_address")
	val hotelAddress: String? = null,

	@field:SerializedName("request_array")
	val requestArray: Int? = null,

	@field:SerializedName("hotel_lat")
	val hotelLat: Any? = null,

	@field:SerializedName("hotel_name")
	val hotelName: String? = null,

	@field:SerializedName("city_id")
	val cityId: Int? = null,

	@field:SerializedName("hotel_long")
	val hotelLong: Any? = null
) : Parcelable