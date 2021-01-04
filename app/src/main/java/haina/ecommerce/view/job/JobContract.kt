package haina.ecommerce.view.job

import haina.ecommerce.model.DataItemHaina
import haina.ecommerce.model.DataItemJob

interface JobContract {

    fun successLoadListJob(msg:String)
    fun errorLoadListJob(msg:String)
    fun getLoadListJob(list: List<DataItemJob?>?)
    fun getDataSize(list: Int?)

    fun successLoadJobCategory(msg:String)
    fun errorLoadJobCategory(msg:String)
    fun getLoadJobCategory(itemHaina: MutableList<DataItemHaina?>?)

    fun getLoadListLocation(itemHaina: List<DataItemHaina?>?)
    fun successLoadListLocation(msg:String)
    fun errorLoadListLocation(msg:String)
}