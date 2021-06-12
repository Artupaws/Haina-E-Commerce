package haina.ecommerce.model.flight

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataAirline(

	@field:SerializedName("total_airline")
	val totalAirline: Int? = null,

	@field:SerializedName("access_code")
	val accessCode: String? = null,

	@field:SerializedName("depart")
	val depart: List<DepartItem?>? = null
) : Parcelable