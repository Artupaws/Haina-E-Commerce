package haina.ecommerce.view.history.historytransaction

import haina.ecommerce.base.BaseView
import haina.ecommerce.model.transactionlist.DataTransaction

interface HistoryTransactionContract {

    fun messageGetListTransaction(msg:String)
    fun getListTransaction(data:DataTransaction?)

    interface TransactionUnfinishContract{
        interface View:BaseView{
            fun messageCancelTransaction(msg:String)
        }
    }

}