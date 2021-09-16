package haina.ecommerce.model.vacancy

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import haina.ecommerce.model.EducationDetail
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseGetListApplicant(

	@field:SerializedName("data")
	val data: List<DataListApplicant?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("value")
	val value: Int? = null
) : Parcelable


