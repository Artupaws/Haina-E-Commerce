package haina.ecommerce.model.flight

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CountriesItem(

	@field:SerializedName("countryName")
	val countryName: String? = null,

	@field:SerializedName("countryID")
	val countryID: String? = null
) : Parcelable