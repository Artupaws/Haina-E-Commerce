package haina.ecommerce.view.flight.filldatapassenger

import haina.ecommerce.model.flight.DataRealTicketPrice
import haina.ecommerce.model.flight.DataSetPassenger

interface FillDataPassengerContract {

    fun messageCalculationPrice(msg:String)
    fun messageSetDataPassenger(msg:String)
    fun getCalculationPrice(data: DataRealTicketPrice?)
    fun getIdSetPassenger(data:List<DataSetPassenger>)

}