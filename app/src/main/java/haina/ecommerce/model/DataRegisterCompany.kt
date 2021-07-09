package haina.ecommerce.model

import com.google.gson.annotations.SerializedName

data class DataRegisterCompany(

	@field:SerializedName("icon_url")
	val iconUrl: String? = null,

	@field:SerializedName("address")
	val address: List<Any?>? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("photo")
	val photo: List<Any?>? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("status")
	val status: String? = null
)