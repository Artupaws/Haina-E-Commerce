package haina.ecommerce.model

import com.google.gson.annotations.SerializedName

data class DataCurrency(
		@field:SerializedName("date")
		val date: String? = null,

		@field:SerializedName("rates")
		val currency: Currency? = null,

		@field:SerializedName("base")
		val base: String? = null
)