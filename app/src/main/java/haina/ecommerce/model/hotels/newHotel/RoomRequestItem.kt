package haina.ecommerce.model.hotels.newHotel

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class RoomRequestItem(

	@field:SerializedName("childNum")
	val childNum: Int? = null,

	@field:SerializedName("isRequestChildBed")
	val isRequestChildBed: Boolean? = null,

	@field:SerializedName("childAges")
	val childAges:  @RawValue Any? = null,

	@field:SerializedName("roomType")
	val roomType: Int? = null
) : Parcelable