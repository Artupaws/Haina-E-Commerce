package haina.ecommerce.view.history.historysubmitapplication

import haina.ecommerce.model.DataJobApplication
import haina.ecommerce.model.vacancy.MyApplication

interface HistorySubmitJobContract {

    fun getListSubmitJob(item:List<MyApplication?>?)
    fun messageGetSubmitJobSuccess(msg:String)
    fun messageGetSubmitJobError(msg:String)

}