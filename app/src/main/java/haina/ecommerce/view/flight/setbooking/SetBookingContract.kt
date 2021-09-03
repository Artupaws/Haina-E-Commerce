package haina.ecommerce.view.flight.setbooking

import haina.ecommerce.model.flight.DataRealTicketPrice
import haina.ecommerce.model.flight.DataSetPassenger

interface SetBookingContract {

    fun messageCalculationPrice(msg:String)
    fun messageSetDataPassenger(msg:String)
    fun getCalculationPrice(data: DataRealTicketPrice?)
    fun accessCode(accessCode:String?)
    fun getIdSetPassenger(data:List<DataSetPassenger>)

}