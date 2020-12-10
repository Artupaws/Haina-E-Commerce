package haina.ecommerce.model

import com.google.gson.annotations.SerializedName

data class Creator(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("fullname")
	val fullname: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)