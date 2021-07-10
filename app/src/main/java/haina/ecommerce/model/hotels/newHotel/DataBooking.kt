package haina.ecommerce.model.hotels.newHotel

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import haina.ecommerce.model.hotels.transactionhotel.CancelledItem
import haina.ecommerce.model.hotels.transactionhotel.UnpaidItem
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataBooking(

	@field:SerializedName("paid")
	val paid: List<PaidItem?>? = null,

	@field:SerializedName("cancelled")
	val cancelled: List<PaidItem?>? = null,

	@field:SerializedName("unpaid")
	val unpaid: List<PaidItem?>? = null
) : Parcelable