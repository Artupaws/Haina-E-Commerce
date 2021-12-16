package haina.ecommerce.model.property

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseShowProperty(

	@field:SerializedName("data")
	val data: List<DataShowProperty?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("value")
	val value: Int? = null
) : Parcelable

@Parcelize
data class DataShowProperty(

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
	val updatedAt: String? = null,

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

	@field:SerializedName("expiry_date")
	val expiryDate: String? = null,

	@field:SerializedName("rental_price")
	val rentalPrice: Int? = null,

	@field:SerializedName("floor_level")
	val floorLevel: Int? = null,

	@field:SerializedName("certificate_type")
	val certificateType: String? = null,

	@field:SerializedName("facilities")
	val facilities: List<FacilitiesItem?>? = null,

	@field:SerializedName("post_type")
	val postType: String? = null,

	@field:SerializedName("id_transaction")
	val idTransaction: Int? = null,

	@field:SerializedName("images")
	val images: List<ImagesItem?>? = null,

	@field:SerializedName("city")
	val city: CityItem,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("views")
	val views: Int? = null,

	@field:SerializedName("bookmark")
	val bookmark: Int? = null
) : Parcelable

@Parcelize
data class FacilitiesItem(

	@field:SerializedName("id_facility")
	val idFacility: Int? = null,

	@field:SerializedName("facility_name")
	val facilityName: String? = null,

	@field:SerializedName("facility_name_zh")
	val facilityNameZh: String? = null
) : Parcelable

@Parcelize
data class ImagesItem(

	@field:SerializedName("path")
	val path: String? = null,

	@field:SerializedName("filename")
	val filename: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("id_property")
	val idProperty: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("deleted_at")
	val deletedAt: String? = null
) : Parcelable

@Parcelize
data class Owner(

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("birthdate")
	val birthdate: String? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("about")
	val about: String? = null,

	@field:SerializedName("photo")
	val photo: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("firebase_uid")
	val firebaseUid: String? = null,

	@field:SerializedName("email_verified_at")
	val emailVerifiedAt: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("fullname")
	val fullname: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable

@Parcelize
data class CityItem(
	@field:SerializedName("id")
	var id: Int,

	@field:SerializedName("name")
	var nameCity: String? = null,

	@field:SerializedName("province")
	var province: String? = null,

	@field:SerializedName("id_province")
	var idProvince: Int
):Parcelable
