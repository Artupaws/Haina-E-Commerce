package haina.ecommerce.model.property

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseDeleteProperty(
    @SerializedName("message")
    val message:String?,
    @SerializedName("value")
    val value:Int?
):Parcelable