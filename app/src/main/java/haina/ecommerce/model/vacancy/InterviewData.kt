package haina.ecommerce.model.vacancy

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class InterviewData(

    @field:SerializedName("method")
    val method: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("id_vacancy")
    val idVacancy: Int? = null,

    @field:SerializedName("cp_name")
    val cpName: String? = null,

    @field:SerializedName("cp_phone")
    val cpPhone: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("location")
    val location: String? = null,

    @field:SerializedName("id_user")
    val idUser: Int? = null,

    @field:SerializedName("time")
    val time: String? = null,

    @field:SerializedName("id")
    val id: Int? = null
) : Parcelable

