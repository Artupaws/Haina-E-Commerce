package haina.ecommerce.model.hotels

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HotelSearchItem(

	@field:SerializedName("City")
	val city: String? = null,

	@field:SerializedName("ID")
	val iD: String? = null,

	@field:SerializedName("id_city")
	val idCity: Int? = null,

	@field:SerializedName("Type")
	val type: String? = null,

	@field:SerializedName("Name")
	val name: String? = null
) : Parcelable
