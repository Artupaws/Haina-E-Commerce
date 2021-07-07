package haina.ecommerce.model.property

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseGetFacilitiesProperty(
	val data: List<DataFacilitiesProperty?>? = null,
	val message: String? = null,
	val value: Int? = null
) : Parcelable

@Parcelize
data class DataFacilitiesProperty(
	@SerializedName("name_zh")
	val nameZh: String? = null,
	@SerializedName("name")
	val name: String? = null,
	@SerializedName("id")
	val id: Int? = null
) : Parcelable
