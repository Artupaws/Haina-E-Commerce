package haina.ecommerce.view.history.historyshortlistapplicant

import haina.ecommerce.model.DataShortlist
import haina.ecommerce.model.DataShortlistApplicant

interface HistoryShortListContract {

    fun messageGetShortListSuccess(msg:String)
    fun messageGetShortListError(msg:String)
    fun messageInterviewApplicant(msg: String)
    fun messageAcceptApplicant(msg: String)
    fun messageDeclineApplicant(msg: String)
    fun getShortListApplicant(item:List<DataShortlist?>?)

}