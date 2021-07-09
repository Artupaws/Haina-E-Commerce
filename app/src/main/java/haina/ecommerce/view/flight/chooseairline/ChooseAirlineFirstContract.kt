package haina.ecommerce.view.flight.chooseairline

import haina.ecommerce.model.flight.DataAirline

interface ChooseAirlineFirstContract {

    fun messageChooseAirline(msg:String)
    fun accessCode(accessCode:String?)
    fun getDataAirline(data: DataAirline?)
}