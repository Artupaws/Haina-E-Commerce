package haina.ecommerce.model.forum

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseAddMod(
	val dataAddMod: DataAddMod? = null,
	val message: String? = null,
	val value: Int? = null
) : Parcelable

@Parcelize
data class DataAddMod(
	val updatedAt: String? = null,
	val forumAction: String? = null,
	val createdAt: String? = null,
	val id: Int? = null,
	val message: String? = null,
	val subforumId: String? = null
) : Parcelable
