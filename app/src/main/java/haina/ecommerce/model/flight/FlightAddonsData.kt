package haina.ecommerce.model.flight

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FlightAddonsData(

    @field:SerializedName("id")
    val passengerId: Int? = null,

    @field:SerializedName("trip")
    val trip: TripAddonsData? = null,

) : Parcelable