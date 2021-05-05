package haina.ecommerce.model.hotels

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FacilitiesItem(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("facilities_name")
	val facilitiesName: String? = null
) : Parcelable