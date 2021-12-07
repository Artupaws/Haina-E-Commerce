package haina.ecommerce.model.companycatalog

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import haina.ecommerce.model.companycatalog.master.CompanyItemCategory
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseGetCompanyItemCategory(

	@field:SerializedName("data")
	val data: List<CompanyItemCategory?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("value")
	val value: Int? = null
) : Parcelable

