package haina.ecommerce.model.restaurant

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RestaurantData(

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("owner_id")
	val ownerId: Int? = null,

	@field:SerializedName("latitude")
	val latitude: Double? = null,

	@field:SerializedName("weekdays_time_open")
	val weekdaysTimeOpen: String? = null,

	@field:SerializedName("verified")
	val verified: String? = null,

	@field:SerializedName("rating")
	val rating: String? = null,

	@field:SerializedName("weekend_time_close")
	val weekendTimeClose: String? = null,

	@field:SerializedName("cuisine")
	val cuisine: List<CuisineAndTypeData?>? = null,

	@field:SerializedName("photo")
	val photo: List<RestaurantPhoto?>? = null,

	@field:SerializedName("type")
	val type: List<CuisineAndTypeData?>? = null,

	@field:SerializedName("open_days")
	val openDays: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("weekend_time_open")
	val weekendTimeOpen: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("halal")
	val halal: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("weekdays_time_close")
	val weekdaysTimeClose: String? = null,

	@field:SerializedName("open")
	val open: Int? = null,

	@field:SerializedName("longitude")
	val longitude: Double? = null,

	@field:SerializedName("city_id")
	val cityId: Int? = null
) : Parcelable
