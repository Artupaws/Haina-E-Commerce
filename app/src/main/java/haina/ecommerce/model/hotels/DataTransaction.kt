package haina.ecommerce.model.hotels

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataTransaction (

    @SerializedName("id")
    val idTransaction:Int,

    @SerializedName("hotel_id")
    val hotelId:Int,

    @SerializedName("user_id")
    val userId:Int,

    @SerializedName("check_in")
    val checkIn:String,

    @SerializedName("check_out")
    val checkOut:String,

    @SerializedName("total_night")
    val totalNight:Int,

    @SerializedName("total_guest")
    val totalGuest:Int,

    @SerializedName("total_price")
    val totalPrice:Int,

    @SerializedName("status")
    val status:String,

    @SerializedName("order_id")
    val orderId:Int
        ):Parcelable