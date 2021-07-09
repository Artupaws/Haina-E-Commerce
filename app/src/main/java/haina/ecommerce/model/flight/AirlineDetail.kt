package haina.ecommerce.model.flight

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AirlineDetail(
    @SerializedName("airline_code")
    var airlineCode: String,
    @SerializedName("airline_name")
    var airlineName: String,
    @SerializedName("image")
    var image: String,
) : Parcelable