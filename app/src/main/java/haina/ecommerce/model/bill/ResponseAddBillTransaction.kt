package haina.ecommerce.model.bill

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseAddBillTransaction(

    @field:SerializedName("data")
	val dataBillTransaction: DataBillTransaction? = null,

    @field:SerializedName("message")
	val message: String? = null,

    @field:SerializedName("value")
	val value: Int? = null
) : Parcelable