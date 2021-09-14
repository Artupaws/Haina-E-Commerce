package haina.ecommerce.view.history.historyjobvacancy.historyshortlistapplicant

import haina.ecommerce.model.DataJobApplication

interface HistorySubmitContract {

    fun getListSubmitJob(item:List<DataJobApplication?>?)
    fun messageGetSubmitJobSuccess(msg:String)
    fun messageGetSubmitJobError(msg:String)

}