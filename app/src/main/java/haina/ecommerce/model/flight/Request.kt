package haina.ecommerce.model.flight

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import haina.ecommerce.roomdatapassenger.DataPassenger
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
    var totalPassenger: Int,
        @SerializedName("flightClass")
    var flightClass: String,
        @SerializedName("airlinesFirst")
    var airlinesFirst: AirlinesFirst?,
        @SerializedName("airlinesSecond")
    var airlinesSecond: AirlinesSecond?,
        @SerializedName("dataPassenger")
    var dataPassenger: List<DataPassenger>?
):Parcelable