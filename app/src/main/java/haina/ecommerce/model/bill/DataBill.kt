package haina.ecommerce.model.bill

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataBill(

	@field:SerializedName("customer_id")
	val customerId: String? = null,

	@field:SerializedName("customer_name")
	val customerName: String? = null,

	@field:SerializedName("bill_date")
	val billDate: String? = null,

	@field:SerializedName("bill_total")
	val totalBill: String? = null,

	@field:SerializedName("product_code")
	val productCode: String? = null,

	@field:SerializedName("penalty")
	val penalty: String? = null,

	@field:SerializedName("provider_code")
	val providerCode: String? = null
) : Parcelable