package haina.ecommerce.model

import com.google.gson.annotations.SerializedName

data class Jobvacancy(

	@field:SerializedName("id_address")
	val idAddress: Int? = null,

	@field:SerializedName("salary_from")
	val salaryFrom: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("salary_to")
	val salaryTo: Int? = null,

	@field:SerializedName("id_category")
	val idCategory: Int? = null,

	@field:SerializedName("id_company")
	val idCompany: Int? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("photo_url")
	val photoUrl: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)