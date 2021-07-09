package haina.ecommerce.model.hotels

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataHotel(

	@field:SerializedName("hotel_image")
	val hotelImage: String? = null,

	@field:SerializedName("hotel_city")
	val hotelCity: String? = null,

	@field:SerializedName("rooms")
	val rooms: List<RoomsItem?>? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("hotel_address")
	val hotelAddress: String? = null,

	@field:SerializedName("facilities")
	val facilities: List<FacilitiesItem?>? = null,

	@field:SerializedName("hotel_lat")
	val hotelLat: Double? = null,

	@field:SerializedName("hotel_name")
	val hotelName: String? = null,

	@field:SerializedName("city_id")
	val cityId: Int? = null,

	@field:SerializedName("hotel_long")
	val hotelLong: Double? = null,

	@field:SerializedName("starting_price")
	val startingPrice: String? = null,

	@field:SerializedName("avg_rating")
	val avgRating:Float? = null
) : Parcelable