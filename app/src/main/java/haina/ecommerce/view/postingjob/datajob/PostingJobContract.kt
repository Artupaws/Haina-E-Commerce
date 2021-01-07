package haina.ecommerce.view.postingjob.datajob

import haina.ecommerce.model.DataCompany
import haina.ecommerce.model.DataItemHaina
import haina.ecommerce.model.DataMyJob
import haina.ecommerce.model.DataPostingJob

interface PostingJobContract {

    fun successPostingJob(msg: String)
    fun errorPostingJob(msg: String)
    fun getValuePostingJob(item: DataPostingJob?)

    fun successLoadJobCategory(msg:String)
    fun errorLoadJobCategory(msg:String)
    fun loadJobCategory(itemHaina: List<DataItemHaina?>?)

    fun getDataCompany(item: DataCompany)
    fun messageGetDataCompany(msg:String)


}