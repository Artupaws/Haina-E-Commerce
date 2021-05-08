package haina.ecommerce.model.hotels

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import com.google.gson.annotations.SerializedName

@Parcelize
class RequestBookingHotel(
    @SerializedName("hotel_id")
    var hotelId: Int?,

    @SerializedName("room_id")
    var roomId: Int?,

    @SerializedName("check_in")
    var checkIn: String?,

    @SerializedName("check_out")
    var checkOut: String?,

    @SerializedName("total_guest")
    var totalGuest: Int?,

    @SerializedName("total_price")
    var totalPrice: String?
) : Parcelable
