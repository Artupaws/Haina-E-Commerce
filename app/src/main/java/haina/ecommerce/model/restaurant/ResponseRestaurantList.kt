package haina.ecommerce.model.restaurant

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import haina.ecommerce.model.restaurant.master.RestaurantPagination
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseRestaurantList(

	@field:SerializedName("data")
	val data: RestaurantPagination? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("value")
	val value: Int? = null
) : Parcelable

