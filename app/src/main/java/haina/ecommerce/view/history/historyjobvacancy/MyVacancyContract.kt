package haina.ecommerce.view.history.historyjobvacancy

import haina.ecommerce.base.BaseView
import haina.ecommerce.model.DataItemHaina
import haina.ecommerce.model.vacancy.DataCreateVacancy
import haina.ecommerce.model.vacancy.DataListApplicant
import haina.ecommerce.model.vacancy.DataMyVacancy

interface MyVacancyContract {

    interface View:BaseView{
        fun messageGetListMyVacancy(msg:String)
        fun getListMyVacancy(data:List<DataMyVacancy?>?)

        fun getDataCreateVacancy(data: DataCreateVacancy?)
        fun messageGetDataCreateVacancy(msg:String)

        fun getLoadListLocation(itemHaina: List<DataItemHaina?>?)
        fun messageLoadListLocation(msg:String)
    }

    interface ViewListApplicant:BaseView{
        fun messageGetListApplicant(msg:String)
        fun getDataListApplicant(data:List<DataListApplicant?>?)
    }
}