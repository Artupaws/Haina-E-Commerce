package haina.ecommerce.model.restaurant

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RestaurantPhoto(

	@field:SerializedName("filename")
	val filename: String? = null,

	@field:SerializedName("uploaded")
	val uploaded: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("url")
	val url: String? = null
) : Parcelable
