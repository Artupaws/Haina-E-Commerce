package haina.ecommerce.model.bill

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RequestBill(

	@field:SerializedName("order_id")
	val orderId: String? = null,

	@field:SerializedName("product_code")
	val productCode: String? = null
) : Parcelable