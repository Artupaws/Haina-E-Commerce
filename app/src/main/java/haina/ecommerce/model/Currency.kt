package haina.ecommerce.model

import com.google.gson.annotations.SerializedName

data class Currency(

	@field:SerializedName("IDR")
	val iDR: String? = null,

	@field:SerializedName("USD")
	val uSD: String? = null,

	@field:SerializedName("CNY")
	val cNY: String? = null
)