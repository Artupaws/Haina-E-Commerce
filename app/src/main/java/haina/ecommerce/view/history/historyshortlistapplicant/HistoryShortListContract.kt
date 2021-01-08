package haina.ecommerce.view.history.historyshortlistapplicant

import haina.ecommerce.model.DataShortlistApplicant

interface HistoryShortListContract {

    fun messageGetShortList(msg:String)
    fun getShortListApplicant(item:List<DataShortlistApplicant?>?)

}