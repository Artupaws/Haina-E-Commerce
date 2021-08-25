package haina.ecommerce.view.posting.newvacancy

import haina.ecommerce.model.DataMyJob

interface VacancyContract {

    fun successLoadMyPost(msg:String)
    fun errorLoadMyPost(msg:String)
    fun getListMyPost(list: List<DataMyJob?>?)

}