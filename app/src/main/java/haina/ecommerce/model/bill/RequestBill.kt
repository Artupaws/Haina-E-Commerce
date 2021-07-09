package haina.ecommerce.model.bill

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RequestBill(

	@field:SerializedName("product_code")
	val productCode: String? = null,

	@field:SerializedName("amount")
	val amount: String? = null,

	@field:SerializedName("admin_fee")
	val adminFee: String? = null,

	@field:SerializedName("customer_number")
	val customerNumber: String? = null,

	@field:SerializedName("id_payment_method")
	val paymentMethod: Int? = null,

	@field:SerializedName("inquiry")
	val inquiry: Int? = null
) : Parcelable