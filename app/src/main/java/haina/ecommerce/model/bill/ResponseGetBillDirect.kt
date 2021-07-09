package haina.ecommerce.model.bill

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseGetBillDirect(

    @field:SerializedName("value")
    val value: Int? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("data")
	val dataInquiry: DataNoInquiry? = null
) : Parcelable