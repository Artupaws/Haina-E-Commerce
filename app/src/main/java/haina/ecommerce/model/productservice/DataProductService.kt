package haina.ecommerce.model.productservice

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataProductService(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id_product_category")
	val idProductCategory: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("photo_url")
	val photoUrl: String? = null,

	@field:SerializedName("id_providers")
	val idProviders: Int? = null,

	@field:SerializedName("product")
	val product:String? = null
) : Parcelable