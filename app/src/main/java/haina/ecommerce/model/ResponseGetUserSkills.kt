package haina.ecommerce.model

import com.google.gson.annotations.SerializedName

data class ResponseGetUserSkills(

    @field:SerializedName("data")
	val data: List<DataSkillsUser?>? = null,

    @field:SerializedName("message")
	val message: String? = null,

    @field:SerializedName("value")
	val value: Int? = null
)