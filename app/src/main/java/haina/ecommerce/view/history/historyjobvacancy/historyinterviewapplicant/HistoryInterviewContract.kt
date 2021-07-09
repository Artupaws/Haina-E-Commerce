package haina.ecommerce.view.history.historyjobvacancy.historyinterviewapplicant

import haina.ecommerce.model.DataShortlist

interface HistoryInterviewContract {

    fun messageStatusSuccess(msg:String)
    fun messageStatusFailed(msg:String)
    fun getInterviewApplicant(item: List<DataShortlist?>?)

}