package haina.ecommerce.model

import com.google.gson.annotations.SerializedName

data class ResponseGetListJob(

    @field:SerializedName("data")
	val data: List<DataJobVacancy?>? = null,

    @field:SerializedName("message")
	val message: String? = null,

    @field:SerializedName("value")
	val value: Int? = null
)