package haina.ecommerce.model.companycatalog.master

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CompanyAddress(

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("id_city")
	val idCity: Int? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("primary_address")
	val primaryAddress: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable
