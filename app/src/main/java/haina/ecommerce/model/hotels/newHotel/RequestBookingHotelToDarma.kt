package haina.ecommerce.model.hotels.newHotel

import com.google.gson.annotations.SerializedName

data class RequestBookingHotelToDarma(

	@SerializedName("smoking_room")
	var smokingRoom: Int,

	@SerializedName("phone")
	var phone: String,

	@SerializedName("special_request")
	var specialRequest: String?,

	@SerializedName("id_payment_method")
	var idPaymentMethod: Int?,

	@SerializedName("paxes")
	var paxes: List<DataGuest>,

	@SerializedName("email")
	var email: String,

	@SerializedName("price")
	var price: String?
)