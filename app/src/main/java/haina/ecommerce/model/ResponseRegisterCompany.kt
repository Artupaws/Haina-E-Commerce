package haina.ecommerce.model

import com.google.gson.annotations.SerializedName

data class ResponseRegisterCompany(

    @field:SerializedName("data")
	val dataRegisterCompany: DataRegisterCompany? = null,

    @field:SerializedName("message")
	val message: String? = null,

    @field:SerializedName("value")
	val value: Int? = null
)