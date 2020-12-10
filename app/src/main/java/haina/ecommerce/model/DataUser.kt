package haina.ecommerce.model

import com.google.gson.annotations.SerializedName

data class DataUser(

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("photo")
	val photo: Any? = null,

	@field:SerializedName("fullname")
	val fullname: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)