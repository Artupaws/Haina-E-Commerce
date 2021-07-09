package haina.ecommerce.model.hotels.newHotel

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataSetBooking(

	@field:SerializedName("room_id")
	val roomId: Int? = null,

	@field:SerializedName("os_ref_no")
	val osRefNo: Any? = null,

	@field:SerializedName("total_price")
	val totalPrice: String? = null,

	@field:SerializedName("booking_date")
	val bookingDate: Any? = null,

	@field:SerializedName("hotel_id")
	val hotelId: Int? = null,

	@field:SerializedName("check_in")
	val checkIn: String? = null,

	@field:SerializedName("cancelation_policy")
	val cancelationPolicy: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("reservation_no")
	val reservationNo: Any? = null,

	@field:SerializedName("agent_os_ref")
	val agentOsRef: String? = null,

	@field:SerializedName("requests")
	val requests: String? = null,

	@field:SerializedName("check_out")
	val checkOut: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("payment")
	val payment: Payment? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("breakfast")
	val breakfast: String? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable