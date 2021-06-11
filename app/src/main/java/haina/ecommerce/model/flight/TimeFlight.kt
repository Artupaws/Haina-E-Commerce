package haina.ecommerce.model.flight

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TimeFlight (
    @SerializedName("origin")
    var origin:String,
    @SerializedName("destination")
    var destination:String,
    @SerializedName("depart_time")
    var departureTime:String,
    @SerializedName("arrival_time")
    var arrivedTime:String
    ):Parcelable