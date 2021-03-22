package haina.ecommerce.model.pulsaanddata

import com.google.gson.annotations.SerializedName

data class DataItem(

		@field:SerializedName("product")
	val paketData: List<PaketDataItem?>? = null,

		@field:SerializedName("name")
	val name: String? = null
)