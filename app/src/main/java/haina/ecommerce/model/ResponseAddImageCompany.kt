package haina.ecommerce.model

import com.google.gson.annotations.SerializedName

data class ResponseAddImageCompany(

        @field:SerializedName("data")
	val dataAddImageCompany: DataAddImageCompany? = null,

        @field:SerializedName("message")
	val message: String? = null,

        @field:SerializedName("value")
	val value: Int? = null
)