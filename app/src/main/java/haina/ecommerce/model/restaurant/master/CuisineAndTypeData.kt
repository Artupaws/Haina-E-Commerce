package haina.ecommerce.model.restaurant.master

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CuisineAndTypeData(

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("name_zh")
    val nameZh: String? = null
) : Parcelable

