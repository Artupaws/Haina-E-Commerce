package haina.ecommerce.model.forum

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseGetListBan(

	@field:SerializedName("data")
	val data: List<DataItemBan?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("value")
	val value: Int? = null
) : Parcelable

@Parcelize
data class DataItemBan(

	@field:SerializedName("subforum_image")
	val subforumImage: String? = null,

	@field:SerializedName("category_zh")
	val categoryZh: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("followed")
	val followed: Boolean? = null,

	@field:SerializedName("posts")
	val posts: List<DataItemHotPost?>? = null,

	@field:SerializedName("category_id")
	val categoryId: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("total_poster")
	val totalPoster: Int? = null,

	@field:SerializedName("ban_reason")
	val banReason: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("creator_id")
	val creatorId: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("category")
	val category: String? = null
) : Parcelable


