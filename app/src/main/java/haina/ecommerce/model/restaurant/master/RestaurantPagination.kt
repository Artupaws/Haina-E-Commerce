package haina.ecommerce.model.restaurant.master

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import haina.ecommerce.model.restaurant.master.RestaurantData
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RestaurantPagination(

    @field:SerializedName("total")
	val total: Int? = null,

    @field:SerializedName("total_page")
	val totalPage: Int? = null,

    @field:SerializedName("restaurants")
	val restaurants: List<RestaurantData?>? = null,

    @field:SerializedName("current_page")
	val currentPage: Int? = null
) : Parcelable
