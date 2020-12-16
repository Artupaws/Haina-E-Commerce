package haina.ecommerce.view.postingjob

import haina.ecommerce.model.DataItemHaina
import haina.ecommerce.model.DataPostingJob

interface PostingJobContract {

    fun successPostingJob(msg: String)
    fun errorPostingJob(msg: String)
    fun getValuePostingJob(item: DataPostingJob?)

    fun successLoadJobCategory(msg:String)
    fun errorLoadJobCategory(msg:String)
    fun loadJobCategory(itemHaina: List<DataItemHaina?>?)
    fun loadJobLocation(itemHaina: List<DataItemHaina?>?)



}