package haina.ecommerce.model.hotels

data class Bookings (val bookingId:String, val bookingStatus:String,
                     val checkInDate:String, val checkoutDate:String, val totalGuests:String,  val cityHotel:String,
                     val totalNight:String, val typeRoom:String,
                     val nameHotel:String, val reviews:String?, val rating:Float?)