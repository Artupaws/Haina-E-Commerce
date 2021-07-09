package haina.ecommerce.model.hotels.transactionhotel

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataTransactionHotel(

	@field:SerializedName("paid")
	val paid: List<PaidItem?>? = null,

	@field:SerializedName("cancelled")
	val cancelled: List<CancelledItem?>? = null,

	@field:SerializedName("unpaid")
	val unpaid: List<UnpaidItem?>? = null
) : Parcelable