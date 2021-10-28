package haina.ecommerce.model.restaurant

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import haina.ecommerce.model.restaurant.master.CuisineAndTypeData
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseCuisineAndTypeList(

    @field:SerializedName("data")
    val data: List<CuisineAndTypeData?>? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("value")
    val value: Int? = null
):Parcelable
