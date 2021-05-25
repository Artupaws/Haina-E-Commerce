package haina.ecommerce.model.bill

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataBillNoInquiry(

	@field:SerializedName("bill_amount")
	val billAmount: String? = null,

	@field:SerializedName("bill_date")
	val billDate: String? = null,

	@field:SerializedName("admin_fee")
	val adminFee: String? = null,

	@field:SerializedName("customer_name")
	val customerName: String? = null,

	@field:SerializedName("customer_id")
	val customerId: String? = null,

	@field:SerializedName("total_bill")
	val totalBill: String? = null
) : Parcelable