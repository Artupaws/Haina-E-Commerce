package haina.ecommerce.model

import com.google.gson.annotations.SerializedName

data class DataPostingJob(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("creator")
	val creator: Creator? = null,

	@field:SerializedName("jobcategory")
	val jobcategory: String? = null,

	@field:SerializedName("salary_to")
	val salaryTo: Int? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("salary_from")
	val salaryFrom: Int? = null,

	@field:SerializedName("location")
	val location: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("photo_url")
	val photoUrl: String? = null,

	@field:SerializedName("time")
	val time: String? = null,

	@field:SerializedName("subcategory")
	val subcategory: Subcategory? = null,

	@field:SerializedName("status")
	val status: String? = null
)