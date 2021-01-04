package haina.ecommerce.model

import com.google.gson.annotations.SerializedName

data class ResponseAddAddressCompany(

		@field:SerializedName("data")
	val dataAddAddressCompany: DataAddAddressCompany? = null,

		@field:SerializedName("message")
	val message: String? = null,

		@field:SerializedName("value")
	val value: Int? = null
)