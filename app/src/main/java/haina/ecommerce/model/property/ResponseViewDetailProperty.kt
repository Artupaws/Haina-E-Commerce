package haina.ecommerce.model.property

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseViewDetailProperty(

	@field:SerializedName("data")
	val dataViewDetailProperty: DataDetailProperty? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("value")
	val value: Int? = null

) : Parcelable

@Parcelize
data class DataDetailProperty(

	@field:SerializedName("selling_price")
	val sellingPrice: Int? = null,

	@field:SerializedName("year")
	val year: String? = null,

	@field:SerializedName("latitude")
	val latitude: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("land_area")
	val landArea: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: Any? = null,

	@field:SerializedName("property_type")
	val propertyType: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("bathroom")
	val bathroom: Int? = null,

	@field:SerializedName("longitude")
	val longitude: String? = null,

	@field:SerializedName("owner")
	val owner: Owner? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("building_area")
	val buildingArea: String? = null,

	@field:SerializedName("id_user")
	val idUser: Int? = null,

	@field:SerializedName("deleted_at")
	val deletedAt: String? = null,

	@field:SerializedName("bedroom")
	val bedroom: Int? = null,

	@field:SerializedName("condition")
	val condition: String? = null,

	@field:SerializedName("id_city")
	val idCity: Int? = null,

	@field:SerializedName("post_date")
	val postDate: String? = null,

	@field:SerializedName("rental_price")
	val rentalPrice: Int? = null,

	@field:SerializedName("floor_level")
	val floorLevel: Int? = null,

	@field:SerializedName("certificate_type")
	val certificateType: String? = null,

	@field:SerializedName("facilities")
	val facilities: List<FacilitiesItem?>? = null,

	@field:SerializedName("images")
	val images: List<ImagesItem?>? = null,

	@field:SerializedName("city")
	val city: CityItem,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("views")
	val views: Int? = null
) : Parcelable
