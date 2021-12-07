package haina.ecommerce.model.companycatalog.master

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CompanyItem(

    @field:SerializedName("item_catalog")
    val itemCatalog: String? = null,

    @field:SerializedName("item_price")
    val itemPrice: Int? = null,

    @field:SerializedName("item_name")
    val itemName: String? = null,

    @field:SerializedName("company")
    val company: CompanyData? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("id_item_catalog")
    val idItemCatalog: Int? = null,

    @field:SerializedName("media")
    val media: List<CompanyItemMedia?>? = null,

    @field:SerializedName("item_category_zh")
    val itemCategoryZh: String? = null,

    @field:SerializedName("item_description")
    val itemDescription: String? = null,

    @field:SerializedName("promoted")
    val promoted: Int? = null,

    @field:SerializedName("id_item_category")
    val idItemCategory: Int? = null,

    @field:SerializedName("item_category")
    val itemCategory: String? = null
) : Parcelable
