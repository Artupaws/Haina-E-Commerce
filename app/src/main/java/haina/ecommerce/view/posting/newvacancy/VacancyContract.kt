package haina.ecommerce.view.posting.newvacancy

import haina.ecommerce.base.BaseView
import haina.ecommerce.model.DataMyJob

interface VacancyContract {

    interface ViewCreateVacancyFree:BaseView{
        fun messageCreateVacancy(msg:String)
    }

}