package haina.ecommerce.model.flight

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PriceDetailItem(

	@field:SerializedName("bagInfo")
	val bagInfo: Any? = null,

	@field:SerializedName("priceInfo")
	val priceInfo: Any? = null,

	@field:SerializedName("paxType")
	val paxType: String? = null,

	@field:SerializedName("baseFare")
	val baseFare: Int? = null,

	@field:SerializedName("totalFare")
	val totalFare: Int? = null,

	@field:SerializedName("tax")
	val tax: Int? = null
) : Parcelable