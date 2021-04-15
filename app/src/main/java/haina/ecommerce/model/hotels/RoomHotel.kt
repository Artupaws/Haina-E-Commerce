package haina.ecommerce.model.hotels

data class RoomHotel (val typeRoom:String, val imageRoom:List<ImageRoom?>? = null, val typeBed:String, val roomsLeft:Int?, val price:String)