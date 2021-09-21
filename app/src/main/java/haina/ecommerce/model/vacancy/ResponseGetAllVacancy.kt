package haina.ecommerce.model.vacancy

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import haina.ecommerce.model.PhotoItemDataCompany
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseGetAllVacancy(

	@field:SerializedName("data")
	val data: List<DataAllVacancy?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("value")
	val value: Int? = null
) : Parcelable

@Parcelize
data class DataAllVacancy(

	@field:SerializedName("type_name")
	val typeName: String? = null,

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

	@field:SerializedName("bookmarked")
	val bookmarked: Int? = null,

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

	@field:SerializedName("edu_name")
	val eduName: String? = null,

	@field:SerializedName("id_specialist")
	val idSpecialist: Int? = null,

	@field:SerializedName("max_salary")
	val maxSalary: Int? = null,

	@field:SerializedName("city_name")
	val cityName: String? = null,

	@field:SerializedName("id_city")
	val idCity: Int? = null,

	@field:SerializedName("level_name")
	val levelName: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("package_name")
	val packageName: String? = null,

	@field:SerializedName("min_salary")
	val minSalary: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("position")
	val position: String? = null,

	@field:SerializedName("company_name")
	val companyName: String? = null,

	@field:SerializedName("specialist_name")
	val specialistName: String? = null,

	@field:SerializedName("pinned")
	val pinned: String? = null,

	@field:SerializedName("company_photo")
	val companyPhoto: List<CompanyPhotoItem?>? = null,

	@field:SerializedName("company_url")
	val photoCompany: String? = null
) : Parcelable

@Parcelize
data class CompanyPhotoItem(
	@field:SerializedName("id")
	val id:Int,
	@field:SerializedName("id_company")
	val idCompany:Int,
	@field:SerializedName("photo_url")
	val photoUrl:String,
	@field:SerializedName("name")
	val name:String
):Parcelable
