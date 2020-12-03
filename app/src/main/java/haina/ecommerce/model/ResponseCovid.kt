package haina.ecommerce.model

import com.google.gson.annotations.SerializedName

data class ResponseCovid(

	@field:SerializedName("data")
	val data: List<DataCovid?>? = null
)