package haina.ecommerce.model.categorypost

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseGetCategoryPost(

    @field:SerializedName("data")
	val data: List<DataCategory?>? = null,

    @field:SerializedName("message")
	val message: String? = null,

    @field:SerializedName("value")
	val value: Int? = null
) : Parcelable