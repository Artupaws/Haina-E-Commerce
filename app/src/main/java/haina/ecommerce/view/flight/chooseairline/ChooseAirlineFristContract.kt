package haina.ecommerce.view.flight.chooseairline

import haina.ecommerce.model.flight.DataAirline

interface ChooseAirlineFristContract {

    fun messageChooseAirline(msg:String)
    fun accessCode(accessCode:String?)
    fun getDataAirline(data: DataAirline?)
}