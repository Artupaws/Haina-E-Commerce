package haina.ecommerce.model.flight

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SeatAddOnsItem(

	@field:SerializedName("departTime")
	val departTime: String? = null,

	@field:SerializedName("arrivalTime")
	val arrivalTime: String? = null,

	@field:SerializedName("origin")
	val origin: String? = null,

	@field:SerializedName("destination")
	val destination: String? = null,

	@field:SerializedName("infos")
	val infos: List<SeatInfosItem?>? = null
) : Parcelable