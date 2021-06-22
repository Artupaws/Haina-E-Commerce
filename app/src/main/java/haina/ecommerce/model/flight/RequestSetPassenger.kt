package haina.ecommerce.model.flight

import com.google.gson.annotations.SerializedName
import haina.ecommerce.room.roomdatapassenger.DataPassenger

data class RequestSetPassenger(
    @SerializedName("contact_title")
    var contactTitle: String,

    @SerializedName("contact_first_name")
    var contactFirstName: String,

    @SerializedName("contact_last_name")
    var contactLastName: String,

    @SerializedName("contact_country_code_phone")
    var codePhone: String,

    @SerializedName("contact_area_code_phone")
    var areaPhone: String,

    @SerializedName("contact_remaining_phone_no")
    var remainPhone: String,

    @SerializedName("insurance")
    var insurance: String?,

    @SerializedName("pax_details")
    var paxDetail: List<DataPassenger>
)