package haina.ecommerce.model.vacancy

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseJobBookmark(

	@field:SerializedName("data")
	val data: DataBookmark? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("value")
	val value: Int? = null
) : Parcelable

@Parcelize
data class DataBookmark(

	@field:SerializedName("id_vacancy")
	val idVacancy: String? = null,

	@field:SerializedName("id_user")
	val idUser: Int? = null
) : Parcelable
