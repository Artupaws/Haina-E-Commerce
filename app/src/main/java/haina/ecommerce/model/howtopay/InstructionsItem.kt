package haina.ecommerce.model.howtopay

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class InstructionsItem(

	@field:SerializedName("how_to")
	val howTo: String? = null,

	@field:SerializedName("payment_media")
	val paymentMedia: String? = null,

	@field:SerializedName("id_payment_method")
	val idPaymentMethod: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("how_to_zh")
	val howToZh: String? = null
) : Parcelable