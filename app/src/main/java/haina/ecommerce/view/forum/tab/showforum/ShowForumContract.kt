package haina.ecommerce.view.forum.tab.showforum

import haina.ecommerce.base.BaseView
import haina.ecommerce.model.forum.*

interface ShowForumContract {

    interface View:BaseView{
        fun messageGetCategory(msg:String)
        fun messageGetListForum(msg:String)
        fun messageGetListSubforum(msg:String)
        fun messageGiveUpvote(msg:String)
        fun messageGetAllThreads(msg:String)
        fun getListCategoryForum(data:List<DataCategoryForum?>?)
        fun getListForum(data:List<DataItemHotPost?>?)
        fun getListSubforum(data:List<SubforumData?>?)
        fun getListAllThreads(data:List<ThreadsItem?>?)
        fun getTotalPage(totalPageParams:Int)
    }

    interface ViewDetailMySubforum:BaseView{
        fun messageGiveUpvote(msg:String)
        fun getListPost(data:DataAllThreads)

        fun messageListPost(msg:String)
        fun getSubforumData(data:SubforumEngagement)


        fun messageFollowSubforum(msg:String)


    }

}