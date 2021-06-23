package haina.ecommerce.model.flight

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SeatInfosItem(

	@field:SerializedName("seatType")
	val seatType: String? = null,

	@field:SerializedName("isOpen")
	val isOpen: Boolean? = null,

	@field:SerializedName("seatDesignator")
	val seatDesignator: String? = null,

	@field:SerializedName("compartment")
	val compartment: String? = null,

	@field:SerializedName("X")
	var X: Int? = null,

	@field:SerializedName("width")
	val width: Int? = null,

	@field:SerializedName("Y")
	var Y: Int? = null,

	@field:SerializedName("currency")
	val currency: String? = null,

	@field:SerializedName("seatPrice")
	val seatPrice: Int? = null,

	@field:SerializedName("seatText")
	val seatText: String? = null,

	@field:SerializedName("assignable")
	val assignable: Boolean? = null,

	@field:SerializedName("height")
	val height: Int? = null
) : Parcelable