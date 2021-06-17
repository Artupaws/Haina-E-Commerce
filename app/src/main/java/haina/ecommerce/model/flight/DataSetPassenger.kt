package haina.ecommerce.model.flight

import android.os.Parcelable
import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataSetPassenger (

    @SerializedName("id")
    var id:Int,

    @SerializedName("id_flight_booking_session")
    var idFlightBookingSeason:Int,

    @SerializedName("first_name")
    var first_name:String,

    @SerializedName("last_name")
    var last_name:String,

    @SerializedName("birth_date")
    var birth_date:String,

    @SerializedName("gender")
    var gender:String,

    @SerializedName("nationality")
    var nationality:String,

    @SerializedName("birth_country")
    var birth_country:String,

    @SerializedName("id_number")
    var id_number:String?,

    @SerializedName( "title")
    var title:String,

    @SerializedName("parent")
    var parent:String,

    @SerializedName("passport_number")
    var passport_number:String,

    @SerializedName("passport_issued_country")
    var passport_issued_country:String?,

    @SerializedName("passport_issued_date")
    var passport_issued_date:String?,

    @SerializedName( "passport_expired_date")
    var passport_expired_date:String?,

    @SerializedName("type")
    var type:String,

):Parcelable