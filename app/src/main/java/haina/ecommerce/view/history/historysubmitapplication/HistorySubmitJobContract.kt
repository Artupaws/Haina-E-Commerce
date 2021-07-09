package haina.ecommerce.view.history.historysubmitapplication

import haina.ecommerce.model.DataJobApplication

interface HistorySubmitJobContract {

    fun getListSubmitJob(item:List<DataJobApplication?>?)
    fun messageGetSubmitJobSuccess(msg:String)
    fun messageGetSubmitJobError(msg:String)

}