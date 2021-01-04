package haina.ecommerce.model

import com.google.gson.annotations.SerializedName

data class DataAddImageCompany(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("photo_url")
	val photoUrl: String? = null
)