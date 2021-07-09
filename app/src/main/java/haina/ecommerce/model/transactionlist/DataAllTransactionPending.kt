package haina.ecommerce.model.transactionlist

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataAllTransactionPending(

	@field:SerializedName("product")
	val product: String? = null,

	@field:SerializedName("total_amount")
	val totalAmount: Int? = null,

	@field:SerializedName("customer_number")
	val customerNumber: String? = null,

	@field:SerializedName("icon")
	val icon: String? = null,

	@field:SerializedName("transaction_time")
	val transactionTime: String? = null,

	@field:SerializedName("id_payment_method")
	val idPaymentMethod: Int? = null,

	@field:SerializedName("order_id")
	val orderId: String? = null,

	@field:SerializedName("payment_method")
	val paymentMethod: String? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable