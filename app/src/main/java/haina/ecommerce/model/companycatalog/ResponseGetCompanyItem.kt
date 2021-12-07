package haina.ecommerce.model.companycatalog

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import haina.ecommerce.model.companycatalog.master.CompanyData
import haina.ecommerce.model.companycatalog.master.CompanyItem
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseGetCompanyItem(

	@field:SerializedName("data")
	val data: List<CompanyItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("value")
	val value: Int? = null
) : Parcelable
