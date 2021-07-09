package haina.ecommerce.model.hotels.newHotel

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PaymentMethod(

    @SerializedName("id")
    var id:Int? = null,

    @SerializedName("id_payment_method_category")
    var idPaymentMethodCategory:Int? = null,

    @SerializedName("name")
    var name:String? = null,

    @SerializedName("photo_url")
    var photoUrl:String? = null,

    @SerializedName("category")
    var category:CategoryDataBooking? = null,
):Parcelable
