package haina.ecommerce.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserDocument(

	@field:SerializedName("docs_url")
	val docsUrl: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("docs_name")
	val docsName: String? = null,

	@field:SerializedName("docs_category")
	val docsCategory: String? = null
) : Parcelable