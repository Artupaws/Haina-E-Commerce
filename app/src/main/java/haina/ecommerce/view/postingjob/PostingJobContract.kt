package haina.ecommerce.view.postingjob

import android.widget.ArrayAdapter
import haina.ecommerce.model.DataItem

interface PostingJobContract {

    fun successPostingJob(msg: String)
    fun errorPostingJob(msg: String)
    fun successLoadJobCategory(msg:String)
    fun errorLoadJobCategory(msg:String)

    fun loadJobCategory(item: ArrayList<DataItem?>?)



}