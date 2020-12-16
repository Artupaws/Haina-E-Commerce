package haina.ecommerce.model

import com.google.gson.annotations.SerializedName

data class DataItemJob(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("creator")
	val creator: Creator? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("photo_url")
	val photoUrl: String? = null,

	@field:SerializedName("time")
	val time: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("subcategory")
	val subcategory: Subcategory? = null,

	@field:SerializedName("status")
	val status: String? = null
)