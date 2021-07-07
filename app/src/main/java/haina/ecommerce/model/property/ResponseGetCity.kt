package haina.ecommerce.model.property

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseGetCity(

	@field:SerializedName("data")
	val data: List<DataCity?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("value")
	val value: Int? = null
) : Parcelable

@Parcelize
data class DataCity(

	@field:SerializedName("id_darma")
	val idDarma: Int? = null,

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("id_province")
	val idProvince: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
) : Parcelable
