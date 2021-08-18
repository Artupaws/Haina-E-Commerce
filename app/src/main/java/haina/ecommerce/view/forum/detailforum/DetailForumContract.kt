package haina.ecommerce.view.forum.detailforum

import haina.ecommerce.base.BaseView
import haina.ecommerce.model.forum.DataComment

interface DetailForumContract {

    interface View:BaseView{
        fun messageGetComment(msg:String)
        fun messageNewComment(msg:String)
        fun messageFollowSubforum(msg:String)
        fun messageDeleteComment(msg:String)
        fun messageAssignSubMod(msg:String)
        fun getListComment(data:List<DataComment?>?)
    }

    interface ViewProfileSubforum:BaseView{
        fun messageFollowSubforum(msg:String)
    }

}