package haina.ecommerce.model.hotels.newHotel

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RequestBookingHotelDarma(

	@field:SerializedName("smoking_room")
	val smokingRoom: Boolean,

	@field:SerializedName("phone")
	val phone: String,

	@field:SerializedName("special_request")
	val specialRequest: String,

	@field:SerializedName("id_payment_method")
	val idPaymentMethod: Int?,

	@field:SerializedName("paxes")
	val paxes: List<DataGuest>,

	@field:SerializedName("email")
	val email: String
) : Parcelable