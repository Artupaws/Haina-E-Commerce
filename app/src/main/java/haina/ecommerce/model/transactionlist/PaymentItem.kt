package haina.ecommerce.model.transactionlist

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PaymentItem(

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("payment_status")
	val paymentStatus: String? = null,

	@field:SerializedName("id_transaction")
	val idTransaction: Int? = null,

	@field:SerializedName("midtrans_id")
	val midtransId: String? = null,

	@field:SerializedName("id_payment_method")
	val idPaymentMethod: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("va_number")
	val vaNumber:String? = null,

	@field:SerializedName("settlement_time")
	val settlementTime: Any? = null
) : Parcelable