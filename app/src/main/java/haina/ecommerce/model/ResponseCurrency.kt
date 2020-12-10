package haina.ecommerce.model

import com.google.gson.annotations.SerializedName

data class ResponseCurrency(

        @field:SerializedName("data")
	val dataCurrency: DataCurrency? = null,

        @field:SerializedName("message")
	val message: String? = null,

        @field:SerializedName("value")
	val value: Int? = null
)