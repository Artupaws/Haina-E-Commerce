package haina.ecommerce.model.vacancy

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataListApplicant(


    @field:SerializedName("interview_data")
    val interviewData: InterviewData? = null,

    @field:SerializedName("applicant_notes")
    val applicantNotes: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("id_resume")
    val idResume: Int? = null,

    @field:SerializedName("id_vacancy")
    val idVacancy: Int? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("id_user")
    val idUser: Int? = null,

    @field:SerializedName("user")
    val user: DataUser? = null,

    @field:SerializedName("status")
    val status: String? = null
) : Parcelable
