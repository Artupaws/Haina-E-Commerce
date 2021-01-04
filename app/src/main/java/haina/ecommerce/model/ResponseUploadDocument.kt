package haina.ecommerce.model

import com.google.gson.annotations.SerializedName

data class ResponseUploadDocument(

    @field:SerializedName("data")
	val dataUploadDocument: DataUploadDocument? = null,

    @field:SerializedName("message")
	val message: String? = null,

    @field:SerializedName("value")
	val value: Int? = null
)