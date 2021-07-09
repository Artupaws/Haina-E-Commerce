package haina.ecommerce.model

import com.google.gson.annotations.SerializedName

data class DataShortlistApplicant(

	@field:SerializedName("id_job_vacancy")
	val idJobVacancy: Int? = null,

	@field:SerializedName("jobvacancy")
	val jobvacancy: Jobvacancy? = null,

	@field:SerializedName("id_user_docs")
	val idUserDocs: Int? = null,

	@field:SerializedName("userdocs")
	val userdocs: Userdocs? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("id_user")
	val idUser: Int? = null,

	@field:SerializedName("user")
	val user: User? = null,

	@field:SerializedName("status")
	val status: String? = null
)