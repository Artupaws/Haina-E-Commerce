package haina.ecommerce.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataMyJob(

		@field:SerializedName("date")
	val date: String? = null,

		@field:SerializedName("creator")
	val creator: Creator? = null,

		@field:SerializedName("jobcategory")
	val jobcategory: String? = null,

		@field:SerializedName("salary_to")
	val salaryTo: Int? = null,

		@field:SerializedName("jobapplicant")
	val jobapplicant: List<JobapplicantItem?>? = null,

		@field:SerializedName("description")
	val description: String? = null,

		@field:SerializedName("title")
	val title: String? = null,

		@field:SerializedName("salary_from")
	val salaryFrom: Int? = null,

		@field:SerializedName("skill")
	val skill: List<SkillRequires?>? = null,

		@field:SerializedName("location")
	val location: String? = null,

		@field:SerializedName("id")
	val id: Int? = null,

		@field:SerializedName("photo_url")
	val photoUrl: String? = null,

		@field:SerializedName("time")
	val time: String? = null,

		@field:SerializedName("address")
	val address: AddressItemCompany? = null,

		@field:SerializedName("status")
	val status: String? = null
) : Parcelable