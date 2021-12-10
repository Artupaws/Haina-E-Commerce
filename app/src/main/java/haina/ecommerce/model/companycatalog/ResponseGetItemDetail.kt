package haina.ecommerce.model.companycatalog

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import haina.ecommerce.model.companycatalog.master.CompanyItem
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseGetItemDetail(

	@field:SerializedName("data")
	val data: CompanyItem? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("value")
	val value: Int? = null
) : Parcelable
