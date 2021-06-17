package haina.ecommerce.model.flight

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MealInfosItem(

	@field:SerializedName("fare")
	val fare: Int? = null,

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("currency")
	val currency: String? = null,

	@field:SerializedName("desc")
	val desc: String? = null
) : Parcelable