package haina.ecommerce.model.flight

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataNationality(

	@field:SerializedName("respMessage")
	val respMessage: String? = null,

	@field:SerializedName("respTime")
	val respTime: String? = null,

	@field:SerializedName("countries")
	val countries: List<CountriesItem>,

	@field:SerializedName("accessToken")
	val accessToken: String? = null,

	@field:SerializedName("userID")
	val userID: String? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable