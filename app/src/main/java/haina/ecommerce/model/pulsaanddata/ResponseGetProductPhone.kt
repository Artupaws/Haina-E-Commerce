package haina.ecommerce.model.pulsaanddata

import com.google.gson.annotations.SerializedName

data class ResponseGetProductPhone(

		@field:SerializedName("data")
	val productPhone: ProductPhone? = null,

		@field:SerializedName("message")
	val message: String? = null,

		@field:SerializedName("value")
	val value: Int? = null
)