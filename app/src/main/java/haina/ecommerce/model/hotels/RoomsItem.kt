package haina.ecommerce.model.hotels

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RoomsItem(

	@field:SerializedName("room_name")
	val roomName: String? = null,

	@field:SerializedName("room_price")
	val roomPrice: Int? = null,

	@field:SerializedName("room_image")
	val roomImage: List<RoomImageItem?>? = null,

	@field:SerializedName("hotel_id")
	val hotelId: Int? = null,

	@field:SerializedName("room_bed_id")
	val roomBedId: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("room_bed_type")
	val roomBedType: String? = null,

	@field:SerializedName("room_total")
	val roomTotal: Int? = null,

	@field:SerializedName("room_maxguest")
	val roomMaxguest: Int? = null
) : Parcelable