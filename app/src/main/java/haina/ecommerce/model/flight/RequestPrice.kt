package haina.ecommerce.model.flight

import com.google.gson.annotations.SerializedName

data class RequestPrice(
    @SerializedName("airline")
    var airlineCode: String,

    @SerializedName("depart")
    var airlineDepart: DepartItem?,

    @SerializedName("return")
    var airlineReturn: DepartItem?,

    @SerializedName("airline_access_code")
var accessCode: String?
)