package haina.ecommerce.model

import com.google.gson.annotations.SerializedName

data class Company(

	@field:SerializedName("icon_url")
	val iconUrl: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)