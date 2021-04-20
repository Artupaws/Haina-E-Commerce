package haina.ecommerce.model.flight

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Request (
    @SerializedName("startDate")
    var startDate: String,
    @SerializedName("finishDate")
    var finishDate: String?,
    @SerializedName("fromDestination")
    var fromDestination: String,
    @SerializedName("toDestination")
    var toDestination: String,
    @SerializedName("totalPassenger")
    var totalPassenger: String,
    @SerializedName("flightClass")
    var flightClass: String,
    @SerializedName("airlinesFirst")
    var airlinesFirst: String?,
    @SerializedName("airlinesSecond")
    var airlinesSecond: String?
):Parcelable