package haina.ecommerce.model.hotels.newHotel

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.ArrayList

@Parcelize
data class RequestBookingHotelDarma(

	@SerializedName("smoking_room")
	var smokingRoom: Boolean,

	@SerializedName("phone")
	var phone: String,

	@SerializedName("special_request")
	var specialRequest: String?,

	@SerializedName("id_payment_method")
	var idPaymentMethod: Int?,

	@SerializedName("paxes")
	var paxes: List<DataGuest>,

//	@SerializedName("special_request_array_complete")
//	var special_request_array_complete: ArrayList<SpecialRequestArrayItem>?,

	@SerializedName("email")
	var email: String,

	@SerializedName("price")
	var price: String?
) : Parcelable