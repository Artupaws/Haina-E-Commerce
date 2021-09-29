package haina.ecommerce.model.forum

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseSubforumData(

	@field:SerializedName("data")
	val data: SubforumEngagement? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("value")
	val value: Int? = null
) : Parcelable

@Parcelize
data class SubforumEngagement(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("followers_count")
	val followersCount: Int? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("post_count")
	val postCount: Int? = null,

	@field:SerializedName("subforum_name")
	val subforumName: String? = null,

	@field:SerializedName("subforum_id")
	val subforumId: Int? = null,

	@field:SerializedName("views")
	val views: Int? = null,

	@field:SerializedName("likes")
	val likes: Int? = null,

	@field:SerializedName("following")
	val following: Boolean? = null
) : Parcelable
