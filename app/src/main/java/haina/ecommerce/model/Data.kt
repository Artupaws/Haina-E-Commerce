package haina.ecommerce.model

import com.google.gson.annotations.SerializedName

data class Data(

	@field:SerializedName("fullname")
	val fullname: String? = null,

	@field:SerializedName("token")
	val token: String? = null
)