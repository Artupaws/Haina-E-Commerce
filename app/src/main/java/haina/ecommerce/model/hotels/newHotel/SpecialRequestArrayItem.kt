package haina.ecommerce.model.hotels.newHotel

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SpecialRequestArrayItem(

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("ID")
	val iD: String? = null
) : Parcelable