package haina.ecommerce.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataUser(

	@field:SerializedName("fullname")
	val fullname: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("birthdate")
	val birthdate: String? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("about")
	val about: String? = null,

	@field:SerializedName("photo")
	val photo: String? = null,

	@field:SerializedName("education")
	val lastEducation: String? = null,

	@field:SerializedName("education_detail")
	val educationDetail: EducationDetail? = null,

	@field:SerializedName("latest_work")
	val latestWork: LatestWork? = null,
):Parcelable

@Parcelize
data class EducationDetail(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("id_user")
	val idUser: Int,

	@field:SerializedName("institution")
	val institution: String,

	@field:SerializedName("year_start")
	val yearStart: String,

	@field:SerializedName("year_end")
	val yearEnd: String,

	@field:SerializedName("gpa")
	val gpa: String,

	@field:SerializedName("major")
	val major: String,

	@field:SerializedName("id_edu")
	val idEdu: Int,

	@field:SerializedName("city")
	val city: String,

	@field:SerializedName("create_at")
	val createdAt: String,

	@field:SerializedName("update_at")
	val updatedAt: String
	):Parcelable

@Parcelize
data class LatestWork(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("id_user")
	val idUser: Int,

	@field:SerializedName("company")
	val company: String,

	@field:SerializedName("date_start")
	val dateStart: String,

	@field:SerializedName("date_end")
	val dateEnd: String,

	@field:SerializedName("position")
	val position: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("salary")
	val salary: Int,

	@field:SerializedName("city")
	val city: String,

	@field:SerializedName("create_at")
	val createdAt: String,

	@field:SerializedName("update_at")
	val updatedAt: String
):Parcelable