package haina.ecommerce.model.property

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseCreatePostProperty(
	val data: DataPostProperty? = null,
	val message: String? = null,
	val value: Int? = null
) : Parcelable

@Parcelize
data class DataPostProperty(
	val sellingPrice: String? = null,
	val address: String? = null,
	val year: String? = null,
	val latitude: String? = null,
	val buildingArea: String? = null,
	val landArea: String? = null,
	val idUser: Int? = null,
	val title: String? = null,
	val bedroom: String? = null,
	val condition: String? = null,
	val idCity: String? = null,
	val postDate: String? = null,
	val rentalPrice: String? = null,
	val propertyType: String? = null,
	val floorLevel: String? = null,
	val certificateType: String? = null,
	val facilities: String? = null,
	val bathroom: String? = null,
	val longitude: String? = null,
	val status: String? = null
) : Parcelable
