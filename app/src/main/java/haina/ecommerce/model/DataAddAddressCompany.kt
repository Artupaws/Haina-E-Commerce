package haina.ecommerce.model

import com.google.gson.annotations.SerializedName

data class DataAddAddressCompany(

	@field:SerializedName("id_city")
	val idCity: String? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("id_company")
	val idCompany: String? = null,

	@field:SerializedName("active")
	val active: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)