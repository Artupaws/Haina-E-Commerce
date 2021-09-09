package haina.ecommerce.model.hotels

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HotelSearchItem(

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("ID")
	val iD: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("Name")
	val name: String? = null
) : Parcelable
