package haina.ecommerce.model.restaurant.master

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReviewUserData(

    @field:SerializedName("full_name")
    val fullName: String? = null,

    @field:SerializedName("user_id")
    val userId: Int? = null,

    @field:SerializedName("user_photo")
    val userPhoto: String? = null,

    @field:SerializedName("username")
    val username: String? = null
) : Parcelable