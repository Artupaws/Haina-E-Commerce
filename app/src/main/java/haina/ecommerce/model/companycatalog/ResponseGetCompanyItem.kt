package haina.ecommerce.model.companycatalog

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import haina.ecommerce.model.companycatalog.master.CompanyData
import haina.ecommerce.model.companycatalog.master.CompanyItem
import haina.ecommerce.model.companycatalog.master.PaginationCompanyItem
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseGetCompanyItem(

	@field:SerializedName("data")
	val data: PaginationCompanyItem? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("value")
	val value: Int? = null
) : Parcelable
