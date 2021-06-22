package haina.ecommerce.model.hotels.newHotel

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataHotelDarma(

	@field:SerializedName("respMessage")
	val respMessage: String? = null,

	@field:SerializedName("checkOutDate")
	val checkOutDate: String? = null,

	@field:SerializedName("hotels")
	val hotels: List<HotelsItem?>? = null,

	@field:SerializedName("roomRequest")
	val roomRequest: List<RoomRequestItem?>? = null,

	@field:SerializedName("respTime")
	val respTime: String? = null,

	@field:SerializedName("cityID")
	val cityID: String? = null,

	@field:SerializedName("accessToken")
	val accessToken: String? = null,

	@field:SerializedName("checkInDate")
	val checkInDate: String? = null,

	@field:SerializedName("userID")
	val userID: String? = null,

	@field:SerializedName("paxPassport")
	val paxPassport: String? = null,

	@field:SerializedName("countryID")
	val countryID: String? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable