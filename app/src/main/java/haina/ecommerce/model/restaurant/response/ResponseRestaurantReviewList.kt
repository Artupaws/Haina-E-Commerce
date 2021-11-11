package haina.ecommerce.model.restaurant.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import haina.ecommerce.model.restaurant.master.ReviewPagination
import haina.ecommerce.model.restaurant.master.ReviewPhotoItem
import haina.ecommerce.model.restaurant.master.ReviewUserData
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseRestaurantReviewList(

	@field:SerializedName("data")
	val data: ReviewPagination? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("value")
	val value: Int? = null
) : Parcelable



