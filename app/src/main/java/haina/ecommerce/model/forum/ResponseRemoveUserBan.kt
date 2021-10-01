package haina.ecommerce.model.forum

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseRemoveUserBan(

	@field:SerializedName("data")
	val data: DataRemoveBan? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("value")
	val value: Int? = null
) : Parcelable

@Parcelize
data class DataRemoveBan(

	@field:SerializedName("reason")
	val reason: String? = null,

	@field:SerializedName("mod_id")
	val modId: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("subforum_id")
	val subforumId: Int? = null
) : Parcelable
