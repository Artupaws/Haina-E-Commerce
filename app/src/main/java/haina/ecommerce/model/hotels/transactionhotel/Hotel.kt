package haina.ecommerce.model.hotels.transactionhotel

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Hotel(

	@field:SerializedName("hotel_image")
	val hotelImage: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("hotel_address")
	val hotelAddress: String? = null,

	@field:SerializedName("hotel_lat")
	val hotelLat: Double? = null,

	@field:SerializedName("hotel_name")
	val hotelName: String? = null,

	@field:SerializedName("city_id")
	val cityId: Int? = null,

	@field:SerializedName("hotel_long")
	val hotelLong: Double? = null
) : Parcelable