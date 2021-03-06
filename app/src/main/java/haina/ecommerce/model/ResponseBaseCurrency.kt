package haina.ecommerce.model

import com.google.gson.annotations.SerializedName

data class ResponseBaseCurrency(

		@field:SerializedName("data")
	val data: List<DataCodeCurrency?>? = null,

		@field:SerializedName("message")
	val message: String? = null,

		@field:SerializedName("value")
	val value: Int? = null
)