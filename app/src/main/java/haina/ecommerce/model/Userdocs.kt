package haina.ecommerce.model

import com.google.gson.annotations.SerializedName

data class Userdocs(

	@field:SerializedName("docs_url")
	val docsUrl: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("docs_name")
	val docsName: String? = null,

	@field:SerializedName("docs_category")
	val docsCategory: String? = null
)