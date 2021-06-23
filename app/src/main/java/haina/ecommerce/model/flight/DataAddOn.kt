package haina.ecommerce.model.flight

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataAddOn(

	@field:SerializedName("addOns")
	val addOns: List<AddOnsItem?>? = null,


) : Parcelable