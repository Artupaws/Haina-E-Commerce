package haina.ecommerce.model.forum

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class ResponseSearchForum(

	@field:SerializedName("data")
	val dataSearch: DataSearch? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("value")
	val value: Int? = null
) : Parcelable

@Parcelize
data class PostsItem(

	@field:SerializedName("member_since")
	val memberSince: String? = null,

	@field:SerializedName("comment_count")
	val commentCount: Int? = null,

	@field:SerializedName("images")
	val images: List<ImagesItem?>? = null,

	@field:SerializedName("like_count")
	val likeCount: Int? = null,

	@field:SerializedName("author_data")
	val authorData: AuthorData? = null,

	@field:SerializedName("author")
	val author: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("videos")
	val videos: @RawValue List<Any?>? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("subforum_id")
	val subforumId: Int? = null,

	@field:SerializedName("content")
	val content: String? = null,

	@field:SerializedName("share_count")
	val shareCount: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("author_photo")
	val authorPhoto: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("subforum_data")
	val subforumData: SubforumData? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("view_count")
	val viewCount: Int? = null,

	@field:SerializedName("subforum_follow")
	val subforumFollow: Boolean? = null,

	@field:SerializedName("upvoted")
	val upvoted: Boolean? = null
) : Parcelable

@Parcelize
data class DataSearch(

	@field:SerializedName("post")
	val post: List<ThreadsItem?>? = null,

	@field:SerializedName("subforum")
	val subforum: List<SubforumData?>? = null
) : Parcelable
