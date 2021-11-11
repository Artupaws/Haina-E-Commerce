package haina.ecommerce.model.restaurant.master

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReviewData(

    @field:SerializedName("review_date")
    val reviewDate: String? = null,

    @field:SerializedName("user_id")
    val userId: Int? = null,

    @field:SerializedName("restaurant_id")
    val restaurantId: Int? = null,

    @field:SerializedName("review")
    val review: String? = null,

    @field:SerializedName("rating")
    val rating: Float? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("photos")
    val photos: List<ReviewPhotoItem?>? = null,

    @field:SerializedName("user")
    val user: ReviewUserData? = null,

    @field:SerializedName("restaurant_name")
    val restaurantName: String? = null
) : Parcelable
