package haina.ecommerce.model.paymentmethod

import com.google.gson.annotations.SerializedName

data class DataPaymentMethod(

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("photo_url")
    val photoUrl: String? = null,

    @field:SerializedName("url")
    val url: String? = null,

    @field:SerializedName("paymentmethod")
    val paymentmethod: List<PaymentmethodItem?>? = null
)