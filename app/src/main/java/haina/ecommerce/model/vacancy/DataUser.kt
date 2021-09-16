package haina.ecommerce.model.vacancy

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import haina.ecommerce.model.EducationDetail
import kotlinx.android.parcel.Parcelize


@Parcelize
data class DataUser(

    @field:SerializedName("address")
    val address: String? = null,

    @field:SerializedName("birthdate")
    val birthdate: String? = null,

    @field:SerializedName("education")
    val education: EducationDetail? = null,

    @field:SerializedName("gender")
    val gender: String? = null,

    @field:SerializedName("expected_salary")
    val expectedSalary: Int? = null,

    @field:SerializedName("about")
    val about: String? = null,

    @field:SerializedName("photo")
    val photo: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("firebase_uid")
    val firebaseUid: String? = null,

    @field:SerializedName("email_verified_at")
    val emailVerifiedAt: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("phone")
    val phone: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("fullname")
    val fullname: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("work_experience")
    val workExperience: WorkExperience? = null,

    @field:SerializedName("username")
    val username: String? = null,

    @field:SerializedName("status")
    val status: String? = null
) : Parcelable
