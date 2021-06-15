package haina.ecommerce.view.flight.detailfilldata

import haina.ecommerce.model.flight.DataNationality

interface DetailFillDataContract {

    fun messageGetCountryList(msg:String)
    fun getListCountry(data:DataNationality)

}