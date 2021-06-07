package haina.ecommerce.model.howtopay

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Data(

	@field:SerializedName("instructions")
	val instructions: List<InstructionsItem?>? = null,

	@field:SerializedName("icon")
	val icon: String? = null,

	@field:SerializedName("payment")
	val payment: String? = null
) : Parcelable