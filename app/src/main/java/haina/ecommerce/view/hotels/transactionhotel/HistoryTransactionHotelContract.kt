package haina.ecommerce.view.hotels.transactionhotel

import haina.ecommerce.model.transactionlist.DataTransaction

interface HistoryTransactionHotelContract {

    fun messageGetListTransactionHotel(msg:String)
    fun getListTransactionHotel(data:List<haina.ecommerce.model.hotels.DataTransaction?>?)

}