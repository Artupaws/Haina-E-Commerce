package haina.ecommerce.model.paymentmethod

import com.google.gson.annotations.SerializedName

data class ResponsePaymentMethod(

        @field:SerializedName("data")
    val data: List<DataPaymentMethod?>? = null,

        @field:SerializedName("message")
    val message: String? = null,

        @field:SerializedName("value")
    val value: Int? = null
)