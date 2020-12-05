package haina.ecommerce.model

import com.google.gson.annotations.SerializedName

data class DataItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)