package haina.ecommerce.model

import com.google.gson.annotations.SerializedName

data class JobapplicantItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("gender")
	val gender: Any? = null,

	@field:SerializedName("about")
	val about: Any? = null,

	@field:SerializedName("photo")
	val photo: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("fullname")
	val fullname: String? = null,

	@field:SerializedName("time")
	val time: String? = null,

	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("user_document")
	val userDocument: UserDocument? = null
)