package haina.ecommerce.model.service

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategoryService(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("name_zh")
	val nameZh: String? = null,

	@field:SerializedName("icon")
	val iconCode: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("id_service_category")
	val idServiceCategory: Int? = null
) : Parcelable