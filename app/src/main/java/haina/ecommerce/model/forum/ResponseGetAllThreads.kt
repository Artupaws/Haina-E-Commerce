package haina.ecommerce.model.forum

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseGetAllThreads(

	@field:SerializedName("data")
	val dataAllThreads: DataAllThreads? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("value")
	val value: Int? = null
) : Parcelable


@Parcelize
data class DataAllThreads(

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("total_page")
	val totalPage: Int? = null,

	@field:SerializedName("threads")
	val threads: List<ThreadsItem?>? = null,

	@field:SerializedName("current_page")
	val currentPage: Int? = null
) : Parcelable


@Parcelize
data class ThreadsItem(

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

	@field:SerializedName("created")
	val created: String? = null,

	@field:SerializedName("videos")
	val videos: List<VideosItem?>? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("content")
	val content: String? = null,

	@field:SerializedName("share_count")
	val shareCount: Int? = null,

	@field:SerializedName("author_photo")
	val authorPhoto: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("upvoted")
	val upvoted: Boolean? = null,

	@field:SerializedName("subforum_data")
	val subforumData: SubforumData? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("view_count")
	val viewCount: Int? = null,

	@field:SerializedName("subforum_follow")
	val subforumFollow: Boolean? = null
) : Parcelable
