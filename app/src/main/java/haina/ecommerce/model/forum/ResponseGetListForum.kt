package haina.ecommerce.model.forum

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseGetListForum(

	@field:SerializedName("data")
	val data: List<DataForum?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("value")
	val value: Int? = null
) : Parcelable

@Parcelize
data class DataForum(

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

@Parcelize
data class DataSubforumHotPost(

	@field:SerializedName("subforum_image")
	val subforumImage: String? = null,

	@field:SerializedName("category_id")
	val categoryId: Int? = null,

	@field:SerializedName("category")
	val category: String,

	@field:SerializedName("category_zh")
	val categoryZh: String,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("creator_id")
	val creatorId: Int? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("total_post")
	val totalPost: Int? = null,

	@field:SerializedName("total_poster")
	val totalPoster: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("posts")
	val posts: List<DataForum?>? = null
) : Parcelable

@Parcelize
data class ImagesItem(
	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("post_id")
	val post_id: Int,

	@field:SerializedName("filename")
	val author: String? = null,

	@field:SerializedName("path")
	var path: String
):Parcelable

@Parcelize
data class VideosItem(
	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("post_id")
	val post_id: Int,

	@field:SerializedName("filename")
	val author: String? = null,

	@field:SerializedName("path")
	var path: String
):Parcelable