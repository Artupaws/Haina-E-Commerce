package haina.ecommerce.model

import com.google.gson.annotations.SerializedName

data class ResponseGetJob(

        @field:SerializedName("data")
	val data: List<DataItemJob?>? = null,

        @field:SerializedName("message")
	val message: String? = null,

        @field:SerializedName("value")
	val value: Int? = null
)