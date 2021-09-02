package haina.ecommerce.view.job

import haina.ecommerce.base.BaseView
import haina.ecommerce.model.DataCompany
import haina.ecommerce.model.DataItemHaina
import haina.ecommerce.model.DataItemJob
import haina.ecommerce.model.vacancy.DataAllVacancy
import haina.ecommerce.model.vacancy.DataCreateVacancy

interface JobContract {

    interface View:BaseView{
        fun messageLoadListJob(msg:String)
        fun getLoadListJob(list: List<DataItemJob?>?)
        fun getDataSize(list: Int?)

        fun messageLoadJobCategory(msg:String)
        fun getLoadJobCategory(itemHaina: MutableList<DataItemHaina?>?)

        fun getLoadListLocation(itemHaina: List<DataItemHaina?>?)
        fun messageLoadListLocation(msg:String)

        fun getDataCreateVacancy(data:DataCreateVacancy?)
        fun messageGetDataCreateVacancy(msg:String)

        fun messageCheckRegisterCompany(msg:String)
        fun getDataRegisterCompany(data:DataCompany)

        fun messageGetAllVacancy(msg:String)
        fun getDataAllVacancy(data:List<DataAllVacancy?>?)
    }
}