package haina.ecommerce.model.checkout

import com.google.gson.annotations.SerializedName

data class ResponseCheckout(

        @field:SerializedName("data")
	val dataCheckout: DataCheckout? = null,

        @field:SerializedName("message")
	val message: String? = null,

        @field:SerializedName("value")
	val value: Int? = null
)