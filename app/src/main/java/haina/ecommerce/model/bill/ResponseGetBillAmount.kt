package haina.ecommerce.model.bill

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseGetBillAmount(

    @field:SerializedName("rq_uuid")
	val rqUuid: String? = null,

    @field:SerializedName("amount")
	val amount: String? = null,

    @field:SerializedName("bill_amount")
	val billAmount: String? = null,

    @field:SerializedName("admin_fee")
	val adminFee: String? = null,

    @field:SerializedName("data")
	val dataBill: DataBill? = null,

    @field:SerializedName("error_desc")
	val errorDesc: String? = null,

    @field:SerializedName("error_code")
	val errorCode: String? = null,

    @field:SerializedName("rs_datetime")
	val rsDatetime: String? = null,

    @field:SerializedName("order_id")
	val orderId: String? = null
) : Parcelable