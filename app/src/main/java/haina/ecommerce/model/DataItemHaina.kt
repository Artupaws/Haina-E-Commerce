package haina.ecommerce.model

import com.google.gson.annotations.SerializedName

data class DataItemHaina(

		@field:SerializedName("name")
		val name: String? = null,

		@field:SerializedName("display_name")
		val displayName: String? = null,

		@field:SerializedName("id")
		val id: Int? = null
)