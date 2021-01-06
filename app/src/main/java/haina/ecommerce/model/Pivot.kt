package haina.ecommerce.model

import com.google.gson.annotations.SerializedName

data class Pivot(

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("job_skill_id")
	val jobSkillId: Int? = null
)