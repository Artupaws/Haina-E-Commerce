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
    var airlinesFirst: Airlines?,
    @SerializedName("airlinesSecond")
    var airlinesSecond: Airlines?,
    @SerializedName("dataPassenger")
    var dataPassenger: List<DataPassenger>?
):Parcelable