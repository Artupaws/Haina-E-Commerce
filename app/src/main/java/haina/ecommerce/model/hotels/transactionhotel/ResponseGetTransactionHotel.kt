package haina.ecommerce.model.hotels.transactionhotel

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseGetTransactionHotel(

    @field:SerializedName("data")
	val dataTransactionHotel: DataTransactionHotel? = null,

    @field:SerializedName("message")
	val message: String? = null,

    @field:SerializedName("value")
	val value: Int? = null
) : Parcelable