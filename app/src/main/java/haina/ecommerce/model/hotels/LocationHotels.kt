package haina.ecommerce.model.hotels

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LocationHotels (

    @field:SerializedName("id")
    val idCity:Int,

    @field:SerializedName("name")
    val name:String
):Parcelable