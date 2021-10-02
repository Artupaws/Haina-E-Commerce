package haina.ecommerce.view.forum.tab.profilepost

import haina.ecommerce.base.BaseView
import haina.ecommerce.model.forum.*

interface ProfilePostListContract {

    interface View: BaseView {
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

        fun getListPost(data: DataAllThreads)

        fun messageListPost(msg:String)
    }
}