package haina.ecommerce.model.flight

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RequestSetPassengerAddOn(

	@field:SerializedName("pax_details")
	val paxDetails: List<PaxDataAddons?>? = null
) : Parcelable


