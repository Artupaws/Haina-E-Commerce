package haina.ecommerce.model.forum

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseUnfollowSubforum(

	@field:SerializedName("data")
	val data: DataUnfollowSubforum? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("value")
	val value: Int? = null
) : Parcelable

@Parcelize
data class DataUnfollowSubforum(

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("subforum_id")
	val subforumId: Int? = null
) : Parcelable
