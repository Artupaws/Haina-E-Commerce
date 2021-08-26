package haina.ecommerce.model.flight


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class PaxDataAddons(

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("trip")
    var trip: MutableList<TripAddonsData>? = null,


    @field:SerializedName("total_price")
    var total: Int? = null


    ):Parcelable

