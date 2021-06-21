package haina.ecommerce.model.flight

import com.google.gson.annotations.SerializedName

data class RequestPrice(
    @SerializedName("airline")
    var airlineCode: String,

    @SerializedName("depart")
    var airlineDepart: DepartItem?,

    @SerializedName("return")
    var airlineReturn: DepartItem?,

    @SerializedName("access_code")
var accessCode: String?
)