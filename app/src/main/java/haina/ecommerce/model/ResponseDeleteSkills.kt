package haina.ecommerce.model

import com.google.gson.annotations.SerializedName

data class ResponseDeleteSkills(

	@field:SerializedName("data")
	val data: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("value")
	val value: Int? = null
)