package haina.ecommerce.model.flight

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AvailableDetailItem(

	@field:SerializedName("price")
	val price: Int? = null,

	@field:SerializedName("flightClass")
	val flightClass: String? = null
) : Parcelable