package haina.ecommerce.model.property

import android.os.Parcelable
import android.telecom.Call
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseChangeAvailability(

    @SerializedName("message")
    val message:String?,

    @SerializedName("value")
    val value:Int?
):Parcelable
