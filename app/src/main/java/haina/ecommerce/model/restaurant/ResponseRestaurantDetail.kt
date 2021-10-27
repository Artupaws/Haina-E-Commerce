package haina.ecommerce.model.restaurant

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseRestaurantDetail(

    @field:SerializedName("data")
    val data: RestaurantData? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("value")
    val value: Int? = null
) : Parcelable

