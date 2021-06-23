package haina.ecommerce.model.hotels.newHotel

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataPricePolicy(

	@field:SerializedName("totalPrice")
	val totalPrice: Int? = null,

	@field:SerializedName("specialRequestArray")
	val specialRequestArray: List<SpecialRequestArrayItem?>? = null,

	@field:SerializedName("hotelID")
	val hotelID: String? = null,

	@field:SerializedName("checkInDate")
	val checkInDate: String? = null,

	@field:SerializedName("userID")
	val userID: String? = null,

	@field:SerializedName("paxPassport")
	val paxPassport: String? = null,

	@field:SerializedName("isPackageDeal")
	val isPackageDeal: Boolean? = null,

	@field:SerializedName("cityName")
	val cityName: String? = null,

	@field:SerializedName("checkOutDate")
	val checkOutDate: String? = null,

	@field:SerializedName("bookingType")
	val bookingType: String? = null,

	@field:SerializedName("specialRequestArrayRequired")
	val specialRequestArrayRequired: Boolean? = null,

	@field:SerializedName("respTime")
	val respTime: String? = null,

	@field:SerializedName("isEnableBooking")
	val isEnableBooking: Boolean? = null,

	@field:SerializedName("commission")
	val commission: Int? = null,

	@field:SerializedName("additionalInformation")
	val additionalInformation: String? = null,

	@field:SerializedName("respMessage")
	val respMessage: String? = null,

	@field:SerializedName("cancelPolicy")
	val cancelPolicy: String? = null,

	@field:SerializedName("cityID")
	val cityID: String? = null,

	@field:SerializedName("accessToken")
	val accessToken: String? = null,

	@field:SerializedName("hotelName")
	val hotelName: String? = null,

	@field:SerializedName("countryID")
	val countryID: String? = null,

	@field:SerializedName("roomID")
	val roomID: String? = null,

	@field:SerializedName("roomName")
	val roomName: String? = null,

	@field:SerializedName("isAgentDebtOverdue")
	val isAgentDebtOverdue: Boolean? = null,

	@field:SerializedName("mandatoryPaxPerRoom")
	val mandatoryPaxPerRoom: Boolean? = null,

	@field:SerializedName("isMandatoryFee")
	val isMandatoryFee: Boolean? = null,

	@field:SerializedName("roomRequest")
	val roomRequest: List<RoomRequestItem?>? = null,

	@field:SerializedName("breakfast")
	val breakfast: String? = null,

	@field:SerializedName("bedTypes")
	val bedTypes: Any? = null,

	@field:SerializedName("internalCode")
	val internalCode: String? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable