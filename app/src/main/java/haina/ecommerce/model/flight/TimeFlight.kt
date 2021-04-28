package haina.ecommerce.model.flight

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TimeFlight (val departureTime:String, val arrivedTime:String):Parcelable