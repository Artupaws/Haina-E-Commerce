package haina.ecommerce.model.restaurant.master

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReviewPagination(

    @field:SerializedName("total")
    val total: Int? = null,

    @field:SerializedName("reviews")
    val reviews: List<ReviewData?>? = null,

    @field:SerializedName("total_page")
    val totalPage: Int? = null,

    @field:SerializedName("current_page")
    val currentPage: Int? = null
) : Parcelable