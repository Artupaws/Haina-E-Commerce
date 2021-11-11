package haina.ecommerce.model.restaurant.master

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReviewPhotoItem(

    @field:SerializedName("review_id")
    val reviewId: Int? = null,

    @field:SerializedName("filename")
    val filename: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("photo_url")
    val photoUrl: String? = null,
) : Parcelable