package haina.ecommerce.model.bill

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataBillNoInquiry(

	@field:SerializedName("customer_id")
	val customerId: String? = null,

	@field:SerializedName("amount")
	val amount: String? = null
) : Parcelable