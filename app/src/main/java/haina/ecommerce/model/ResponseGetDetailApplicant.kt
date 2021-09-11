package haina.ecommerce.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseGetDetailApplicant(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("value")
	val value: Int? = null
) : Parcelable

@Parcelize
data class UserDocsItem(

	@field:SerializedName("docs_url")
	val docsUrl: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("id_user")
	val idUser: Int? = null,

	@field:SerializedName("docs_name")
	val docsName: String? = null,

	@field:SerializedName("id_docs_category")
	val idDocsCategory: Int? = null
) : Parcelable

@Parcelize
data class Education(

	@field:SerializedName("institution")
	val institution: String? = null,

	@field:SerializedName("major")
	val major: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("year_start")
	val yearStart: Int? = null,

	@field:SerializedName("year_end")
	val yearEnd: Int? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("id_edu")
	val idEdu: Int? = null,

	@field:SerializedName("gpa")
	val gpa: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("education_level")
	val educationLevel: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("id_user")
	val idUser: Int? = null
) : Parcelable

@Parcelize
data class Data(

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("birthdate")
	val birthdate: String? = null,

	@field:SerializedName("education")
	val education: Education? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("expected_salary")
	val expectedSalary: Int? = null,

	@field:SerializedName("about")
	val about: String? = null,

	@field:SerializedName("photo")
	val photo: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("firebase_uid")
	val firebaseUid: String? = null,

	@field:SerializedName("email_verified_at")
	val emailVerifiedAt: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("user_docs")
	val userDocs: List<UserDocsItem?>? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("fullname")
	val fullname: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("work_experience")
	val workExperience: WorkExperience? = null,

	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable

@Parcelize
data class WorkExperience(

	@field:SerializedName("date_start")
	val dateStart: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("company")
	val company: String? = null,

	@field:SerializedName("date_end")
	val dateEnd: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("id_user")
	val idUser: Int? = null,

	@field:SerializedName("position")
	val position: String? = null,

	@field:SerializedName("salary")
	val salary: Int? = null
) : Parcelable
