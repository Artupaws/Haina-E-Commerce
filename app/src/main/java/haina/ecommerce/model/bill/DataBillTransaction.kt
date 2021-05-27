package haina.ecommerce.model.bill

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataBillTransaction(

	@field:SerializedName("product")
	val productTransaction: ProductTransaction? = null,

	@field:SerializedName("customer_number")
	val customerNumber: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id_user")
	val idUser: Int? = null,

	@field:SerializedName("total_payment")
	val totalPayment: Int? = null,

	@field:SerializedName("id_product")
	val idProduct: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("transaction_time")
	val transactionTime: String? = null,

	@field:SerializedName("payment")
	val paymentTransaction: PaymentTransaction? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("order_id")
	val orderId: String? = null,

	@field:SerializedName("profit")
	val profit: Int? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable