package haina.ecommerce.view.history.historyjobvacancy

import haina.ecommerce.base.BaseView
import haina.ecommerce.model.vacancy.DataMyVacancy

interface MyVacancyContract {

    interface View:BaseView{
        fun messageGetListMyVacancy(msg:String)
        fun getListMyVacancy(data:List<DataMyVacancy?>?)
    }

}