package haina.ecommerce.model.property

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RequestDataProperty (

    @SerializedName("type_property")
    var typeProperty:String,

    @SerializedName("type_posting")
    var typePosting:String,

    @SerializedName("building_area")
    var buildingArea:Int,

    @SerializedName("surface_area")
    var surfaceArea:Int?,

    @SerializedName("bed_room")
    var bedRoom:Int?,

    @SerializedName("bath_room")
    var bathRoom:Int?,

    @SerializedName("floor")
    var floor:Int,

    @SerializedName("facility")
    var facility:ArrayList<String?>?,

    @SerializedName("type_certificate")
    var typeCertificate:String?,

    @SerializedName("province")
    var province:Int,

    @SerializedName("city")
    var city:Int,

    @SerializedName("address")
    var address:String,

    @SerializedName("title")
    var title:String,

    @SerializedName("description")
    var description:String,

    @SerializedName("price")
    var price:String

    ):Parcelable