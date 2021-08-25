package haina.ecommerce.view.job

import haina.ecommerce.base.BaseView
import haina.ecommerce.model.DataItemHaina
import haina.ecommerce.model.DataItemJob

interface JobContract {

    interface View:BaseView{
        fun messageLoadListJob(msg:String)
        fun getLoadListJob(list: List<DataItemJob?>?)
        fun getDataSize(list: Int?)

        fun messageLoadJobCategory(msg:String)
        fun getLoadJobCategory(itemHaina: MutableList<DataItemHaina?>?)

        fun getLoadListLocation(itemHaina: List<DataItemHaina?>?)
        fun messageLoadListLocation(msg:String)
    }
}