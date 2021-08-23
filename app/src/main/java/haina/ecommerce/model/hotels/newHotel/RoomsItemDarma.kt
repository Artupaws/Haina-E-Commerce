package haina.ecommerce.model.hotels.newHotel

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class RoomsItemDarma(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("isPackageDeal")
	val isPackageDeal: Boolean? = null,

	@field:SerializedName("hotelGroup")
	val hotelGroup: Int? = null,

	@field:SerializedName("isOnRequest")
	val isOnRequest: Boolean? = null,

	@field:SerializedName("price")
	val price: Int? = null,

	@field:SerializedName("bookingType")
	val bookingType: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("promoCode")
	val promoCode:  @RawValue Any? = null,

	@field:SerializedName("ID")
	val iD: String? = null,

	@field:SerializedName("facilites")
	val facilites: List<String?>? = null,

	@field:SerializedName("breakfast")
	val breakfast: String? = null
) : Parcelable