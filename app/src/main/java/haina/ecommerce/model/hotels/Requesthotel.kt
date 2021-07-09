package haina.ecommerce.model.hotels

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Requesthotel (
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
    var totalPrice: String?,

    @SerializedName("id_payment_method")
    var idPaymentMethod:Int?
    ):Parcelable