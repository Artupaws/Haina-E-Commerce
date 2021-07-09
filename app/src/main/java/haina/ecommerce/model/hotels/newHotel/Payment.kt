package haina.ecommerce.model.hotels.newHotel

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Payment(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("booking_id")
	val bookingId: Int? = null,

	@field:SerializedName("payment_method_id")
	val paymentMethodId: Int? = null,

	@field:SerializedName("midtrans_id")
	val midtransId: String? = null,

	@field:SerializedName("va_number")
	val vaNumber: String? = null,

	@field:SerializedName("settlement_time")
	val settlementTime: String? = null,

	@field:SerializedName("payment_status")
	val paymentStatus: String? = null
) : Parcelable