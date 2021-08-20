package haina.ecommerce.view.forum.tab.mypost

import haina.ecommerce.base.BaseView
import haina.ecommerce.model.forum.DataForum
import haina.ecommerce.model.forum.DataItemHotPost
import haina.ecommerce.model.forum.DataMypost
import haina.ecommerce.model.forum.DataPostDeleted

interface MyPostContract {

    interface View:BaseView{
        fun messageGetListMypost(msg:String)
        fun messageGiveUpvote(msg:String)
        fun messageDeleteMyPost(msg:String)
        fun getListMypost(data:List<DataItemHotPost?>?)
        fun getDataMyPostDeleted(data: DataPostDeleted)
    }

}