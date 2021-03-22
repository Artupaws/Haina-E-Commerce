package haina.ecommerce.model.pulsaanddata

import com.google.gson.annotations.SerializedName

data class ProductPhone(

	@field:SerializedName("provider")
	val provider: Provider? = null,

	@field:SerializedName("group")
	val group: Group? = null
)