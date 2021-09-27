package haina.ecommerce.view.forum.tab.mypost

import haina.ecommerce.base.BaseView
import haina.ecommerce.model.forum.*

interface MyPostContract {

    interface View:BaseView{
        fun messageGetListMypost(msg:String)
        fun messageGiveUpvote(msg:String)
        fun messageDeleteMyPost(msg:String)
        fun getListMypost(data:List<ThreadsItem?>?)
        fun getDataMyPostDeleted(data: DataPostDeleted)
    }

}