package haina.ecommerce.model.forum

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class ResponseGetHotpost(

	@field:SerializedName("data")
	val data: List<DataItemHotPost?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("value")
	val value: Int? = null
) : Parcelable

@Parcelize
data class SubforumData(

	@field:SerializedName("subforum_image")
	val subforumImage: String? = null,

	@field:SerializedName("category_id")
	val categoryId: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("category_zh")
	val categoryZh: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("creator_id")
	val creatorId: Int? = null,

	@field:SerializedName("creator_username")
	val creatorName: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("post_count")
	val totalPost: Int? = null,

	@field:SerializedName("total_poster")
	val totalPoster: Int? = null,

	@field:SerializedName("subforum_followers")
	val totalFollowers: Int? = null,

	@field:SerializedName("category")
	val category: String? = null,

	@field:SerializedName("posts")
	val posts: List<DataItemHotPost?>? = null
) : Parcelable

@Parcelize
data class DataItemHotPost(

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

	@field:SerializedName("subforum_data")
	val subforumData: SubforumData? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("view_count")
	val viewCount: Int? = null,

	@field:SerializedName("subforum_follow")
    var subforumFollow: Boolean? = null,

	@field:SerializedName("upvoted")
	val upvoted: Boolean? = null
) : Parcelable

@Parcelize
data class AuthorData(

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("birthdate")
	val birthdate: String? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("about")
	val about: String? = null,

	@field:SerializedName("photo")
	val photo: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("firebase_uid")
	val firebaseUid: String? = null,

	@field:SerializedName("email_verified_at")
	val emailVerifiedAt:  @RawValue Any? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("fullname")
	val fullname: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable
