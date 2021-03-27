package haina.ecommerce.model.transactionlist

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataTransaction(

	@field:SerializedName("canceled")
	val canceled: List<CancelItem?>? = null,

	@field:SerializedName("process")
	val process: List<ProcessItem?>? = null,

	@field:SerializedName("success")
	val success: List<SuccessItem?>? = null,

	@field:SerializedName("pending")
	val pending: List<PendingItem?>? = null
) : Parcelable