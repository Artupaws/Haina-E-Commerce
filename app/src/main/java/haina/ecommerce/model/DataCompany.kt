package haina.ecommerce.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataCompany(

    @field:SerializedName("icon_url")
	val iconUrl: String? = null,

    @field:SerializedName("address")
	val addressCompanies: List<AddressItemCompany?>? = null,

    @field:SerializedName("name")
	val name: String? = null,

    @field:SerializedName("description")
	val description: String? = null,

    @field:SerializedName("photo")
	val photoDataCompany: List<PhotoItemDataCompany?>? = null,

    @field:SerializedName("id")
	val id: Int? = null,

    @field:SerializedName("status")
	val status: String? = null
) : Parcelable