package haina.ecommerce.model.forum

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseGetMypost(

	@field:SerializedName("data")
	val data: List<DataItemHotPost?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("value")
	val value: Int? = null
) : Parcelable

@Parcelize
data class DataMypost(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("user_id")
	val userId: Int,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("author")
	val author: String? = null,

	@field:SerializedName("like_count")
	var likeCount: Int,

	@field:SerializedName("view_count")
	var viewCount: Int,

	@field:SerializedName("comment_count")
	val commentCount: Int,

	@field:SerializedName("last_update")
	val lastUpdate: String? = null,

	@field:SerializedName("member_since")
	val memberSince: String? = null,

	@field:SerializedName("content")
	val content: String? = null,

	@field:SerializedName("author_photo")
	val authorPhoto: String? = null,

	@field:SerializedName("images")
	val images: List<ImagesItem?>?,

	@field:SerializedName("videos")
	val videos: List<VideosItem?>?,

	@field:SerializedName("upvoted")
	val upvote: Boolean,

	@field:SerializedName("subforum_follow")
	val subforumFollow: Boolean,

	@field:SerializedName("subforum_data")
	val subforumData: DataSubforumHotPost,

	@field:SerializedName("created")
	val created: String? = null
) : Parcelable

