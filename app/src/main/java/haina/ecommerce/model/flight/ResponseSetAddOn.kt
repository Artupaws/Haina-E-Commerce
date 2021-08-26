package haina.ecommerce.model.flight

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseSetAddOn(

	@field:SerializedName("data")
	val data: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("value")
	val value: Int? = null
) : Parcelable
