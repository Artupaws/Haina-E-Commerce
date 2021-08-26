package haina.ecommerce.model.vacancy

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseGetDataCreateVacancy(

	@field:SerializedName("data")
	val dataCreateVacancy: DataCreateVacancy? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("value")
	val value: Int? = null
) : Parcelable

@Parcelize
data class VacancyEducationItem(

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
) : Parcelable

@Parcelize
data class VacancyTypeItem(

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
) : Parcelable

@Parcelize
data class VacancySkillItem(

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
) : Parcelable

@Parcelize
data class VacancyLevelItem(

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
) : Parcelable

@Parcelize
data class VacancyPackageItem(

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("price")
	val price: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
) : Parcelable

@Parcelize
data class VacancySpecialistItem(

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("name_zh")
	val nameZh: String? = null,

	@field:SerializedName("display_name")
	val displayName: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("photo_url")
	val photoUrl: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
) : Parcelable

@Parcelize
data class DataCreateVacancy(

	@field:SerializedName("vacancy_package")
	val vacancyPackage: List<VacancyPackageItem?>? = null,

	@field:SerializedName("vacancy_level")
	val vacancyLevel: List<VacancyLevelItem?>? = null,

	@field:SerializedName("vacancy_skill")
	val vacancySkill: List<VacancySkillItem?>? = null,

	@field:SerializedName("vacancy_type")
	val vacancyType: List<VacancyTypeItem?>? = null,

	@field:SerializedName("vacancy_category")
	val vacancySpecialist: List<VacancySpecialistItem?>? = null,

	@field:SerializedName("vacancy_education")
	val vacancyEducation: List<VacancyEducationItem?>? = null
) : Parcelable
