package haina.ecommerce.model.forum

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseRemoveModerator(

	@field:SerializedName("data")
	val data: RemoveModeratorData? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("value")
	val value: Int? = null
) : Parcelable

@Parcelize
data class RemoveModeratorData(

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("forum_action")
	val forumAction: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("subforum_id")
	val subforumId: String? = null
) : Parcelable
