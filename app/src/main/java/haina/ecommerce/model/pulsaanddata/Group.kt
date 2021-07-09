package haina.ecommerce.model.pulsaanddata

import com.google.gson.annotations.SerializedName

data class Group(

	@field:SerializedName("pulsa")
	val pulsa: List<PulsaItem?>? = null,

	@field:SerializedName("data")
	val data: List<DataItem?>? = null
)