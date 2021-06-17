package haina.ecommerce.model.flight

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataAddOn(

	@field:SerializedName("respMessage")
	val respMessage: String? = null,

	@field:SerializedName("addOns")
	val addOns: List<AddOnsItem?>? = null,

	@field:SerializedName("respTime")
	val respTime: String? = null,

	@field:SerializedName("accessToken")
	val accessToken: String? = null,

	@field:SerializedName("userID")
	val userID: String? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable