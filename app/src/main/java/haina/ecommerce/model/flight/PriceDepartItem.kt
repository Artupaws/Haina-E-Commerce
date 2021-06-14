package haina.ecommerce.model.flight

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PriceDepartItem(

	@field:SerializedName("psDate")
	val psDate: String? = null,

	@field:SerializedName("classId")
	val classId: Any? = null,

	@field:SerializedName("classFare")
	val classFare: String? = null,

	@field:SerializedName("priceDetail")
	val priceDetail: List<PriceDetailItem?>? = null,

	@field:SerializedName("currency")
	val currency: String? = null,

	@field:SerializedName("garudaAvailability")
	val garudaAvailability: Any? = null,

	@field:SerializedName("airlineSegmentCode")
	val airlineSegmentCode: Any? = null,

	@field:SerializedName("psDestination")
	val psDestination: String? = null,

	@field:SerializedName("flightNumber")
	val flightNumber: String? = null,

	@field:SerializedName("psOrigin")
	val psOrigin: String? = null,

	@field:SerializedName("garudaNumber")
	val garudaNumber: Any? = null
) : Parcelable