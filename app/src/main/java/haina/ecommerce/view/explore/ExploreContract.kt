package haina.ecommerce.view.explore

import haina.ecommerce.model.*
import haina.ecommerce.model.transactionlist.DataAllTransactionPending
import haina.ecommerce.model.transactionlist.DataTransaction
import haina.ecommerce.model.transactionlist.PendingItem

interface ExploreContract {

    fun errorMessage(msg: String?)
    fun successMessage(msg: String?)
    fun messageGetTransactionList(msg:String?)
//    fun getTransactionList(data:DataTransaction?)
    fun getTransactionPending(data:List<DataAllTransactionPending?>?)
    fun dismissShimmerHeadlineNews()
    fun messageGetDataUSer(msg:String)
    fun getDataUser(data : DataUser?)

    fun loadListCodeCurrency(list: List<DataCodeCurrency?>?)
    fun loadCurrency(item: DataCurrency?)
//    fun loadCovidJkt(item: DataCovidJkt?)
//    fun loadHeadlinesNews(list: List<ArticlesItem?>?)

}