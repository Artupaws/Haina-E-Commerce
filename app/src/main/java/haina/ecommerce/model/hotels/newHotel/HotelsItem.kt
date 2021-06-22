package haina.ecommerce.model.hotels.newHotel

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HotelsItem(

	@field:SerializedName("website")
	val website: String? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("rating")
	val rating: Float? = null,

	@field:SerializedName("promoEndDate")
	val promoEndDate: Any? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("market")
	val market: Any? = null,

	@field:SerializedName("bookingDaysBefore")
	val bookingDaysBefore: Int? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("logo")
	val logo: String? = null,

	@field:SerializedName("priceStart")
	val priceStart: Int? = null,

	@field:SerializedName("ID")
	val iD: String? = null,

	@field:SerializedName("availabilityStatus")
	val availabilityStatus: Boolean? = null,

	@field:SerializedName("facilities")
	val facilities: List<String?>? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("internalCode")
	val internalCode: String? = null,

	@field:SerializedName("ratingAverage")
	val ratingAverage: Float? = null
) : Parcelable