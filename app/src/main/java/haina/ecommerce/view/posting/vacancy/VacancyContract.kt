package haina.ecommerce.view.posting.vacancy

import haina.ecommerce.model.DataMyPost

interface VacancyContract {

    fun successLoadMyPost(msg:String)
    fun errorLoadMyPost(msg:String)
    fun getListMyPost(list: List<DataMyPost?>?)

}