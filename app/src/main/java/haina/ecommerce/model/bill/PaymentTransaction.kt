package haina.ecommerce.model.bill

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PaymentTransaction(

	@field:SerializedName("payment_type")
	val paymentType: String? = null,

	@field:SerializedName("amount")
	val amount: String? = null,

	@field:SerializedName("bank")
	val bank: String? = null,

	@field:SerializedName("payment_status")
	val paymentStatus: String? = null,

	@field:SerializedName("virtual_account")
	val virtualAccount: String? = null
) : Parcelable