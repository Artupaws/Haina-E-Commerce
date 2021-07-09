package haina.ecommerce.model.hotels.transactionhotel

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PaidItem(

	@field:SerializedName("room_id")
	val roomId: Int? = null,

	@field:SerializedName("total_price")
	val totalPrice: Int? = null,

	@field:SerializedName("hotel_id")
	val hotelId: Int? = null,

	@field:SerializedName("check_in")
	val checkIn: String? = null,

	@field:SerializedName("rating")
	val rating: Rating? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("check_out")
	val checkOut: String? = null,

	@field:SerializedName("total_guest")
	val totalGuest: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("total_night")
	val totalNight: Int? = null,

	@field:SerializedName("hotel")
	val hotel: Hotel? = null,

	@field:SerializedName("payment")
	val payment: Payment? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("order_id")
	val orderId: String? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable