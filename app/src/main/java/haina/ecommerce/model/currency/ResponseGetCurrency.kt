package haina.ecommerce.model.currency

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseGetCurrency(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("time_next_update_unix")
	val timeNextUpdateUnix: Int? = null,

	@field:SerializedName("conversion_rates")
	val conversionRates: ConversionRates? = null,

	@field:SerializedName("time_next_update_utc")
	val timeNextUpdateUtc: String? = null,

	@field:SerializedName("documentation")
	val documentation: String? = null,

	@field:SerializedName("time_last_update_unix")
	val timeLastUpdateUnix: Int? = null,

	@field:SerializedName("base_code")
	val baseCode: String? = null,

	@field:SerializedName("time_last_update_utc")
	val timeLastUpdateUtc: String? = null,

	@field:SerializedName("terms_of_use")
	val termsOfUse: String? = null
) : Parcelable