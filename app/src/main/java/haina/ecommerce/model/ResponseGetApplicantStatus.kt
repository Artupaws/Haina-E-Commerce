package haina.ecommerce.model

import com.google.gson.annotations.SerializedName

data class ResponseGetApplicantStatus(

        @field:SerializedName("data")
	val statusApplicant: StatusApplicant? = null,

        @field:SerializedName("message")
	val message: String? = null,

        @field:SerializedName("value")
	val value: Int? = null
)