package haina.ecommerce.model.news

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseGetListNewsTable(

    @field:SerializedName("data")
    val data: List<DataNewsTable?>? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("value")
    val value: Int? = null
) : Parcelable