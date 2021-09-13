package haina.ecommerce.model.news

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataNewsTable(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("category")
	val category: Category? = null,

	@field:SerializedName("source_name")
	val sourceName: String? = null,

	@field:SerializedName("url")
	val url: String? = null
) : Parcelable

@Parcelize
data class Category(

	@field:SerializedName("name")
	val name: String? = null,


	@field:SerializedName("id")
	val id: Int? = null
) : Parcelable
