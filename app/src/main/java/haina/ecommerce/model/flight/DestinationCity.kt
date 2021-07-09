package haina.ecommerce.model.flight

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DestinationCity(val nameCity:String, val codeCity:String, val nameAirport:String, val nameCountry:String):Parcelable