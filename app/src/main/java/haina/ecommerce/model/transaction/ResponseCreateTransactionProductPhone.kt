package haina.ecommerce.model.transaction

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseCreateTransactionProductPhone(

    @field:SerializedName("data")
	val dataCreateTransaction: DataCreateTransaction? = null,

    @field:SerializedName("message")
	val message: String? = null,

    @field:SerializedName("value")
	val value: Int? = null
) : Parcelable