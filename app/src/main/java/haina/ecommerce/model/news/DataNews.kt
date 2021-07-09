package haina.ecommerce.model.news

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataNews(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("time")
	val time: String? = null,

	@field:SerializedName("source")
	val source: String? = null,

	@field:SerializedName("source_name")
	val sourceName: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("body")
	val body: String? = null,

	@field:SerializedName("category")
	val category: String? = null
) : Parcelable