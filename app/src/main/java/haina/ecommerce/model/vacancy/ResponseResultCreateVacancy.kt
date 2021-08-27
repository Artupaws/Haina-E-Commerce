package haina.ecommerce.model.vacancy

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseCreateVacancy(

	@field:SerializedName("data")
	val resultCreateVacancy: ResultCreateVacancy? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("value")
	val value: Int? = null
) : Parcelable

@Parcelize
data class ResultCreateVacancy(

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("package")
	val jsonMemberPackage: Int? = null,

	@field:SerializedName("level")
	val level: Int? = null,

	@field:SerializedName("id_company")
	val idCompany: Int? = null,

	@field:SerializedName("id_edu")
	val idEdu: Int? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("type")
	val type: Int? = null,

	@field:SerializedName("experience")
	val experience: Int? = null,

	@field:SerializedName("deleted_at")
	val deletedAt: String? = null,

	@field:SerializedName("salary_display")
	val salaryDisplay: Int? = null,

	@field:SerializedName("id_specialist")
	val idSpecialist: Int? = null,

	@field:SerializedName("max_salary")
	val maxSalary: Int? = null,

	@field:SerializedName("id_city")
	val idCity: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("min_salary")
	val minSalary: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("position")
	val position: String? = null
) : Parcelable
