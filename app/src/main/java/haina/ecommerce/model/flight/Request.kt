package haina.ecommerce.model.flight

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import haina.ecommerce.room.roomdatapassenger.DataPassenger
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
    @SerializedName("totalAdult")
    var totalAdult: Int,
    @SerializedName("totalChild")
    var totalChild: Int,
    @SerializedName("totalBaby")
    var totalBaby: Int,
    @SerializedName("airlinesFirst")
    var airlinesFirst: AirlinesFirst?,
    @SerializedName("airlinesSecond")
    var airlinesSecond: AirlinesSecond?,
    @SerializedName("dataPassenger")
    var dataPassenger: List<DataPassenger?>?,
    @SerializedName("airlineDepart")
        var airlineDepart: List<DepartItem?>?,
    @SerializedName("airlineReturn")
        var airlineReturn: List<DepartItem?>?
):Parcelable