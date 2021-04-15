package haina.ecommerce.model.hotels

data class Hotels(val nameHotel:String, val location:String, val startPrice:String,
val roomHotel:List<RoomHotel?>? = null, val imageRoom:List<ImageRoom?>? = null, val reviewsHotel:List<Reviews?>? = null)