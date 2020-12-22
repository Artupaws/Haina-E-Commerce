package haina.ecommerce.model

import com.google.gson.annotations.SerializedName

data class AddressItem(

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("id_city")
	val idCity: Int? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("status")
	val status: String? = null
)