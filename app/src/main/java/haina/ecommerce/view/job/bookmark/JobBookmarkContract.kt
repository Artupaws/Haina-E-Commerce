package haina.ecommerce.view.job.bookmark

import haina.ecommerce.base.BaseView
import haina.ecommerce.model.DataCompany
import haina.ecommerce.model.DataItemHaina
import haina.ecommerce.model.DataItemJob
import haina.ecommerce.model.vacancy.DataAllVacancy
import haina.ecommerce.model.vacancy.DataCreateVacancy

interface JobBookmarkContract {
    interface View:BaseView{
        fun messageLoadListBookmark(msg:String)
        fun getLoadListBookmark(list: List<DataAllVacancy?>?)
        fun notAvailableBookmark()
        fun messageChangeBookmark(data:Int)
    }
}