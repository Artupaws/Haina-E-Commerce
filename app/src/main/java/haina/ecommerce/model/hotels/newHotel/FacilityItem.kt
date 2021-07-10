package haina.ecommerce.model.hotels.newHotel

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FacilityItem(

	@field:SerializedName("facilityGroupName")
	val facilityGroupName: String? = null,

	@field:SerializedName("facilities")
	val facilities: List<String?>? = null,

	@field:SerializedName("icon")
	val icon: String? = null
) : Parcelable