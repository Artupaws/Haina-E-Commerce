package haina.ecommerce.model.notification

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataItemNotification(

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("id_category")
	val idCategory: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("opened")
	val opened: Int? = null,

	@field:SerializedName("id_user")
	val idUser: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("body")
	val body: String? = null
) : Parcelable