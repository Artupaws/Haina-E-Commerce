package haina.ecommerce.model

import com.google.gson.annotations.SerializedName

data class ResponseGetDataUser(

		@field:SerializedName("data")
	val data: DataUser? = null,

		@field:SerializedName("message")
	val message: String? = null,

		@field:SerializedName("value")
	val value: Int? = null
)