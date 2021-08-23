package haina.ecommerce.model.pulsaanddata

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class ProductPhone(

	@field:SerializedName("provider")
	val provider:  @RawValue Provider? = null,

	@field:SerializedName("group")
	val group:  @RawValue Group? = null
):Parcelable