package haina.ecommerce.model.property

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseGetProvince(
	val data: List<DataProvince?>? = null,
	val message: String? = null,
	val value: Int? = null
) : Parcelable

@Parcelize
data class DataProvince(
	val updatedAt: String? = null,
	@SerializedName("name")
	val name: String? = null,
	val createdAt: String? = null,
	@SerializedName("id")
	val id: Int? = null
) : Parcelable
