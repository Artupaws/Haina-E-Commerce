package haina.ecommerce.model.productservice

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    @field:SerializedName("id")
    val id:Int? = null,

    @field:SerializedName("product_code")
    val productCode:String? = null,

    @field:SerializedName("description")
    val description:String? = null
):Parcelable
