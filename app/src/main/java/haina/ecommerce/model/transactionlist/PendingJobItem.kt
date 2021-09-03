package haina.ecommerce.model.transactionlist

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PendingJobItem(

	@field:SerializedName("category")
	val category: String,

	@field:SerializedName("order_id")
	val orderId: String,

	@field:SerializedName("transaction_time")
	val transactionTime: String,

	@field:SerializedName("product")
	val product: String,

	@field:SerializedName("package")
	val packageAds: String,

	@field:SerializedName("total_payment")
	val totalPayment: Int,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("icon")
	val icon: String,

	@field:SerializedName("payment_method")
	val paymentMethod: String,

	@field:SerializedName("id_payment_method")
	val idPaymentMethod: Int,

	@field:SerializedName("va_number")
	val vaNumber: String,

	@field:SerializedName("midtrans_id")
	val midtransId: String? = null
) : Parcelable