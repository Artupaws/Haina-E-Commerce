package haina.ecommerce.view.flight.filldatapassenger

import haina.ecommerce.model.flight.DataRealTicketPrice

interface FillDataPassengerContract {

    fun messageCalculationPrice(msg:String)
    fun getCalculationPrice(data: DataRealTicketPrice?)

}