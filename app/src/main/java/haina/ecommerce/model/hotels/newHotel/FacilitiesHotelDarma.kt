package haina.ecommerce.model.hotels.newHotel

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FacilitiesHotelDarma(

	@field:SerializedName("facilities_name")
	val facilitiesName: String? = null
) : Parcelable