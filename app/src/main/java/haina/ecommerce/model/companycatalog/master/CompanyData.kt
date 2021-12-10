package haina.ecommerce.model.companycatalog.master

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CompanyData(

    @field:SerializedName("icon_url")
    val iconUrl: String? = null,

    @field:SerializedName("address")
    val address: List<CompanyAddress?>? = null,

    @field:SerializedName("siup")
    val siup: String? = null,

    @field:SerializedName("year")
    val year: Int? = null,

    @field:SerializedName("primary_address")
    val primaryAddress: CompanyAddress? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("media")
    val media: List<CompanyItemMedia?>? = null,

    @field:SerializedName("staff_size")
    val staffSize: Int? = null,

    @field:SerializedName("contact_number")
    val contactNumber: String? = null,

    @field:SerializedName("id_province")
    val idProvince: Int? = null,

    @field:SerializedName("province")
    val province: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("category")
    val category: List<CompanyCategory?>? = null,

    @field:SerializedName("status")
    val status: String? = null
) : Parcelable
