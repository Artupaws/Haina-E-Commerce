package haina.ecommerce.model.flight

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TripAddonsData(

    @field:SerializedName("origin")
    val origin: String? = null,

    @field:SerializedName("destination")
    val destination: String? = null,

    @field:SerializedName("baggage")
    var baggage: String? = null,

    @field:SerializedName("seat")
    var seat: String? = null,

    @field:SerializedName("compartment")
    var compartment: String? = null,

    @field:SerializedName("meals")
    var meals: MutableList<String>? = null,

    ) : Parcelable