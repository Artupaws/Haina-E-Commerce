package haina.ecommerce.model.pulsaanddata

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductPhone(

	@field:SerializedName("provider")
	val provider: Provider? = null,

	@field:SerializedName("group")
	val group: Group? = null
):Parcelable