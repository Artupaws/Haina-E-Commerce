package haina.ecommerce.model.forum

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseForumBannedList(

	@field:SerializedName("data")
	val data: List<DataBannedUser?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("value")
	val value: Int? = null
) : Parcelable

@Parcelize
data class DataBannedUser(

	@field:SerializedName("reason")
	val reason: String? = null,

	@field:SerializedName("mod_id")
	val modId: Int? = null,

	@field:SerializedName("mod")
	val mod: Mod? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("user")
	val user: User? = null,

	@field:SerializedName("subforum_id")
	val subforumId: Int? = null
) : Parcelable

@Parcelize
data class User(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("photo")
	val photo: String? = null,

	@field:SerializedName("username")
	val username: String? = null
) : Parcelable

@Parcelize
data class Mod(

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("photo")
	val photo: String? = null,

	@field:SerializedName("subforum_id")
	val subforumId: Int? = null,

	@field:SerializedName("username")
	val username: String? = null
) : Parcelable
