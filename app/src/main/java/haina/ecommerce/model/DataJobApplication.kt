package haina.ecommerce.model

import com.google.gson.annotations.SerializedName

data class DataJobApplication(

		@field:SerializedName("date")
	val date: String? = null,

		@field:SerializedName("jobdescription")
	val jobdescription: String? = null,

		@field:SerializedName("salary_from")
	val salaryFrom: Int? = null,

		@field:SerializedName("userdocs")
	val userdocs: Userdocs? = null,

		@field:SerializedName("jobtitle")
	val jobtitle: String? = null,

		@field:SerializedName("salary_to")
	val salaryTo: Int? = null,

		@field:SerializedName("jobaddress")
	val jobaddressApplication: JobaddressApplication? = null,

		@field:SerializedName("jobpicture")
	val jobpicture: String? = null,

		@field:SerializedName("company")
	val company: Company? = null,

		@field:SerializedName("id")
	val id: Int? = null,

		@field:SerializedName("time")
	val time: String? = null,

		@field:SerializedName("status")
	val status: String? = null
)