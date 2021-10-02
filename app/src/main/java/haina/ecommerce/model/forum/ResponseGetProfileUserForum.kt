package haina.ecommerce.model.forum

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseGetProfileUserForum(

	@field:SerializedName("data")
	val data: DataProfile? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("value")
	val value: Int? = null
) : Parcelable

@Parcelize
data class DataProfile(

	@field:SerializedName("followers")
	val followers: Int? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("member_since")
	val memberSince: String? = null,

	@field:SerializedName("following")
	val following: Int? = null,

	@field:SerializedName("photo")
	val photo: String? = null,

	@field:SerializedName("post_count")
	val postCount: Int? = null,

	@field:SerializedName("username")
	val username: String? = null
) : Parcelable
