package haina.ecommerce.model.flight

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AddOnsItem(

	@field:SerializedName("baggageInfos")
	val baggageInfos: List<BaggageInfosItem?>? = null,

	@field:SerializedName("origin")
	val origin: String? = null,

	@field:SerializedName("isBaggageBundling")
	val isBaggageBundling: Boolean? = null,

	@field:SerializedName("destination")
	val destination: String? = null,

	@field:SerializedName("mealInfos")
	val mealInfos: List<MealInfosItem?>? = null,

	@field:SerializedName("isEnableNoBaggage")
	val isEnableNoBaggage: Boolean? = null
) : Parcelable