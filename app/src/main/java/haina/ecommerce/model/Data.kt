package haina.ecommerce.model

import com.google.gson.annotations.SerializedName

data class Data(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("rates")
	val rates: Rates? = null,

	@field:SerializedName("base")
	val base: String? = null
)