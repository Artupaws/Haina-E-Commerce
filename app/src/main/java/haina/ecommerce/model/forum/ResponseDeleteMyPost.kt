package haina.ecommerce.model.forum

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseDeleteMyPost(

	@field:SerializedName("data")
	val data: DataPostDeleted? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("value")
	val value: Int? = null
) : Parcelable

@Parcelize
data class DataPostDeleted(

	@field:SerializedName("share_count")
	val shareCount: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("subforum_id")
	val subforumId: Int? = null,

	@field:SerializedName("content")
	val content: String? = null,

	@field:SerializedName("view_count")
	val viewCount: Int? = null
) : Parcelable
