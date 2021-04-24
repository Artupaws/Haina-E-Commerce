package haina.ecommerce.model.flight

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataPassenger (val fullName:String, val birthDate:String, val typePassenger:String):Parcelable