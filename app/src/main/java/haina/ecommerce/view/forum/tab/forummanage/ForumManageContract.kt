package haina.ecommerce.view.forum.tab.forummanage

import haina.ecommerce.base.BaseView
import haina.ecommerce.model.forum.DataBannedUser
import haina.ecommerce.model.forum.DataRemoveBan
import haina.ecommerce.model.forum.Moderator
import haina.ecommerce.model.forum.RemoveModeratorData

interface ForumManageContract {

    interface View: BaseView {
        fun messageGetAbout(msg:String)
        fun getForumDetail(msg:String)
        fun getListModerator(data:List<Moderator?>?)
        fun getListBannedUser(data:List<DataBannedUser?>?)
        fun getRemoveData(data:RemoveModeratorData)

        fun getRemoveBanned(data:DataRemoveBan)



    }
}