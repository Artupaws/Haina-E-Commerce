package haina.ecommerce.view.history.historysubmitapplication.detailapplication

import haina.ecommerce.model.vacancy.DataApplicationDetail


interface DetailApplicationContract {

    fun messageGetApplicationDetail(msg:String)
    fun getApplicationDetail(data:DataApplicationDetail)
}