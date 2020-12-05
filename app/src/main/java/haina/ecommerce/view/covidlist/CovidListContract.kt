package haina.ecommerce.view.covidlist

import haina.ecommerce.model.DataCovid

interface CovidListContract {

    fun errorMessage(msg:String)
    fun loadListCovid(list: List<DataCovid?>?)

}