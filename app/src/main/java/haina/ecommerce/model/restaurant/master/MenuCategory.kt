package haina.ecommerce.model.restaurant.master

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MenuCategory(

	@field:SerializedName("restaurant_id")
	val restaurantId: Int? = null,

	@field:SerializedName("menu_name")
	val menuName: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("menu_images")
	val menuImages: List<MenuImage?>? = null,

	@field:SerializedName("restaurant_name")
	val restaurantName: String? = null
) : Parcelable
