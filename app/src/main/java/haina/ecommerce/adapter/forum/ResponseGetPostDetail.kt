package haina.ecommerce.adapter.forum

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import haina.ecommerce.model.forum.DataItemHotPost
import haina.ecommerce.model.forum.SubforumData
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseGetPostDetail(

    @field:SerializedName("data")
    val data: DataItemHotPost? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("value")
    val value: Int? = null
) : Parcelable
