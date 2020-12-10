package haina.ecommerce.model

import com.google.gson.annotations.SerializedName

data class Login(
		@field:SerializedName("token")
		val token: String? = null,
)