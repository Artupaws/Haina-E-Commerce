package haina.ecommerce.view.history.historysubmitapplication

import haina.ecommerce.model.DataJobApplication

interface HistorySubmitJobContract {

    fun getListSubmitJob(item:List<DataJobApplication?>?)
    fun messageGetSubmitJob(msg:String)

}