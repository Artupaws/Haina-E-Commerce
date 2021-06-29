package haina.ecommerce.model.hotels.newHotel

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Room(

	@field:SerializedName("room_name")
	val roomName: String? = null,

	@field:SerializedName("room_price")
	val roomPrice: String? = null,

	@field:SerializedName("room_image")
	val roomImage: String? = null,

	@field:SerializedName("hotel_id")
	val hotelId: Int? = null,

	@field:SerializedName("id_darma_room")
	val idDarmaRoom: String? = null,

	@field:SerializedName("room_type_id")
	val roomTypeId: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("breakfast")
	val breakfast: String? = null
) : Parcelable