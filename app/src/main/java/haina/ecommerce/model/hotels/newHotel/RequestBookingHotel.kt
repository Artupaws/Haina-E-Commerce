package haina.ecommerce.model.hotels.newHotel

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RequestBookingHotel(

    @SerializedName("country_id")
    var countryID: String,

    @SerializedName("city_id")
    var cityId: Int,

    @SerializedName("pax_passport")
    var paxPassport: String,

    @SerializedName("check_in_date")
    var checkIn: String,

    @SerializedName("check_out_date")
    var checkOut: String

) : Parcelable