package haina.ecommerce.view.job

import haina.ecommerce.model.DataItemHaina
import haina.ecommerce.model.DataJobVacancy

interface JobContract {

    fun successLoadListJob(msg:String)
    fun errorLoadListJob(msg:String)
    fun getLoadListJob(list: List<DataJobVacancy?>?)

    fun successLoadJobCategory(msg:String)
    fun errorLoadJobCategory(msg:String)
    fun getLoadJobCategory(itemHaina: List<DataItemHaina?>?)
}