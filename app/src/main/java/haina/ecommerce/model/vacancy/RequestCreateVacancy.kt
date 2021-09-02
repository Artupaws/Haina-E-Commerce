package haina.ecommerce.model.vacancy

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RequestCreateVacancy(

    @field:SerializedName("position")
    val position:String,

    @field:SerializedName("id_company")
    val idCompany:Int,

    @field:SerializedName("id_specialist")
    val idSpecialist:Int,

    @field:SerializedName("level")
    val level:Int,

    @field:SerializedName("type")
    val type:Int,

    @field:SerializedName("description")
    val description:String,

    @field:SerializedName("experience")
    val experience:Int,

    @field:SerializedName("id_edu")
    val idEdu:Int?,

    @field:SerializedName("min_salary")
    val minSalary:String,

    @field:SerializedName("max_salary")
    val maxSalary:String,

    @field:SerializedName("salary_display")
    val salaryDisplay:Int,

    @field:SerializedName("address")
    val address:String,

    @field:SerializedName("id_city")
    val idCity:Int,

    @field:SerializedName("package")
    val packageAds:Int?,

    @field:SerializedName("payment_method")
    val paymentMethod:Int?,

    @field:SerializedName("skill")
    val skill:String?
):Parcelable
