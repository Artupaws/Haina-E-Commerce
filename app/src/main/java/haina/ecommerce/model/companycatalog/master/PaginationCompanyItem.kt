package haina.ecommerce.model.companycatalog.master

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PaginationCompanyItem(

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("total_page")
	val totalPage: Int? = null,

	@field:SerializedName("items")
	val items: List<CompanyItem?>? = null,

	@field:SerializedName("current_page")
	val currentPage: Int? = null
) : Parcelable

