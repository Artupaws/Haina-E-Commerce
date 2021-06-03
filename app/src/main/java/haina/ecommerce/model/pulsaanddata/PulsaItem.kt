package haina.ecommerce.model.pulsaanddata

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PulsaItem(

	@field:SerializedName("id_product_group")
	val idProductGroup: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("sell_price")
	val   sellPrice: Int? = null,

	@field:SerializedName("base_price")
	val basePrice: Int? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("product_code")
	val productCode: String? = null,

	@field:SerializedName("id_inquiry")
	val idInquiry: Int

):Parcelable