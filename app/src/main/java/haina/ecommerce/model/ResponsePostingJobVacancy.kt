package haina.ecommerce.model

import com.google.gson.annotations.SerializedName

data class ResponsePostingJobVacancy(

		@field:SerializedName("data")
	val dataPostingJob: DataPostingJob? = null,

		@field:SerializedName("message")
	val message: String? = null,

		@field:SerializedName("value")
	val value: Int? = null
)