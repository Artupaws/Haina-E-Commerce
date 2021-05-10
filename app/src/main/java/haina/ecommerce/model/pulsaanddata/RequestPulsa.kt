package haina.ecommerce.model.pulsaanddata

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class RequestPulsa (

    @SerializedName("phone_number")
    var phoneNumber:String,

    @SerializedName("id_product")
    var idProduct:Int,

    @SerializedName("id_payment_method")
    var idPaymentMethod:Int?,

    @SerializedName("total_price")
    var totalPrice:String,

    @SerializedName("type_service")
    var typeService:String
        ):Parcelable