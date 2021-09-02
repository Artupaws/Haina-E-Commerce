package haina.ecommerce.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataItemHaina(

		@field:SerializedName("name")
		val name: String? = null,

		@field:SerializedName("name_zh")
		val nameZh: String? = null,

		@field:SerializedName("display_name")
		val displayName: String? = null,

		@field:SerializedName("id")
		val id: Int? = null,

		@field:SerializedName("photo_url")
		val icon: String? = null
):Parcelable