package haina.ecommerce.view.job.filter

import haina.ecommerce.base.BaseView
import haina.ecommerce.model.DataItemHaina
import haina.ecommerce.model.vacancy.DataAllVacancy
import haina.ecommerce.model.vacancy.DataCreateVacancy

interface BottomSheetFilterContract {
    interface View: BaseView {
        fun messageGetData(msg:String)
        fun getVacancyData(data: DataCreateVacancy?)
        fun getLoadListLocation(data: List<DataItemHaina?>?)
    }
}