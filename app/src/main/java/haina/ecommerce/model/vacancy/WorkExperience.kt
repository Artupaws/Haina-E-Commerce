package haina.ecommerce.model.vacancy

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WorkExperience(

    @field:SerializedName("date_start")
    val dateStart: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("city")
    val city: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("company")
    val company: String? = null,

    @field:SerializedName("date_end")
    val dateEnd: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("id_user")
    val idUser: Int? = null,

    @field:SerializedName("position")
    val position: String? = null,

    @field:SerializedName("salary")
    val salary: Int? = null
) : Parcelable
