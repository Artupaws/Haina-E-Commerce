package haina.ecommerce.model.restaurant.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import haina.ecommerce.model.restaurant.master.RestaurantData
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

