package haina.ecommerce.model.flight

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataRealTicketPrice(

	@field:SerializedName("respMessage")
	val respMessage: String? = null,

	@field:SerializedName("journeyReturnReference")
	val journeyReturnReference: String? = null,

	@field:SerializedName("origin")
	val origin: String? = null,

	@field:SerializedName("destination")
	val destination: String? = null,

	@field:SerializedName("priceReturn")
	val priceReturn: List<PriceReturnItem?>? = null,

	@field:SerializedName("searchKey")
	val searchKey: String? = null,

	@field:SerializedName("accessToken")
	val accessToken: String? = null,

	@field:SerializedName("sumFare")
	val sumFare: Int? = null,

	@field:SerializedName("userID")
	val userID: String? = null,

	@field:SerializedName("airlineID")
	val airlineID: String? = null,

	@field:SerializedName("airlineAccessCode")
	val airlineAccessCode: String? = null,

	@field:SerializedName("journeyDepartReference")
	val journeyDepartReference: String? = null,

	@field:SerializedName("tripType")
	val tripType: String? = null,

	@field:SerializedName("returnDate")
	val returnDate: String? = null,

	@field:SerializedName("paxChild")
	val paxChild: Int? = null,

	@field:SerializedName("paxInfant")
	val paxInfant: Int? = null,

	@field:SerializedName("priceDepart")
	val priceDepart: List<PriceDepartItem?>? = null,

	@field:SerializedName("respTime")
	val respTime: String? = null,

	@field:SerializedName("departDate")
	val departDate: String? = null,

	@field:SerializedName("promoCode")
	val promoCode: String? = null,

	@field:SerializedName("paxAdult")
	val paxAdult: Int? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable