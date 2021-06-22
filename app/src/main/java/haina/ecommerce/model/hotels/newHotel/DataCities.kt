package haina.ecommerce.model.hotels.newHotel

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataCities(

	@field:SerializedName("cities")
	val cities: List<CitiesItem?>? = null,

	@field:SerializedName("ID")
	val iD: String? = null,

	@field:SerializedName("Name")
	val name: String? = null
) : Parcelable