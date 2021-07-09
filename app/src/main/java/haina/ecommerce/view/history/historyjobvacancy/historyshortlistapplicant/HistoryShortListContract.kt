package haina.ecommerce.view.history.historyjobvacancy.historyshortlistapplicant

import haina.ecommerce.model.DataShortlist

interface HistoryShortListContract {

    fun messageGetShortListSuccess(msg:String)
    fun messageGetShortListError(msg:String)
    fun messageInterviewApplicant(msg: String)
    fun messageAcceptApplicant(msg: String)
    fun messageDeclineApplicant(msg: String)
    fun getShortListApplicant(item:List<DataShortlist?>?)

}