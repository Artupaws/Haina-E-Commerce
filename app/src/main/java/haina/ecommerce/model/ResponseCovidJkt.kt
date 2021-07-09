package haina.ecommerce.model

import com.google.gson.annotations.SerializedName

data class ResponseCovidJkt(

		@field:SerializedName("data")
		val dataJkt: DataCovidJkt? = null,

		@field:SerializedName("message")
		val message: String? = null,

		@field:SerializedName("value")
		val value: Int? = null
)