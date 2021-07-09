package haina.ecommerce.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SkillRequires(

	@field:SerializedName("name")
	val name: String? = null
) : Parcelable