package haina.ecommerce.model.news

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseNewsCategory(

	@field:SerializedName("data")
	val data: List<NewsCategory?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("value")
	val value: Int? = null
) : Parcelable

@Parcelize
data class NewsCategory(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("name_zh")
	val nameZh: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
) : Parcelable
