package haina.ecommerce.view.hotels.transactionhotel

import haina.ecommerce.model.hotels.transactionhotel.DataTransactionHotel

interface HistoryTransactionHotelContract {

    fun messageGetListTransactionHotel(msg:String)
    fun getListTransactionHotel(dataHotel: DataTransactionHotel?)

}