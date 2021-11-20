package haina.ecommerce.model.restaurant.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import haina.ecommerce.model.restaurant.master.ReviewData
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseRestaurantAddReview(

    @field:SerializedName("data")
    val data: ReviewData? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("value")
    val value: Int? = null
):Parcelable
