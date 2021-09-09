package haina.ecommerce.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import haina.ecommerce.model.hotels.HotelSearchItem
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseHotelSearch(

	@field:SerializedName("data")
	val data: List<HotelSearchItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("value")
	val value: Int? = null
) : Parcelable
