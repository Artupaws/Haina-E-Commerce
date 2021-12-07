package haina.ecommerce.model.companycatalog.master

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CompanyItemMedia(

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("id_item")
    val idItem: Int? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("media_url")
    val mediaUrl: String? = null,

    @field:SerializedName("deleted_at")
    val deletedAt: String? = null
) : Parcelable

