package haina.ecommerce.view.forum.tab.forumabout

import haina.ecommerce.base.BaseView
import haina.ecommerce.model.forum.*

interface ForumAboutContract {
    interface View: BaseView {
        fun messageGetAbout(msg:String)
        fun getForumDetail(msg:String)
        fun getListModerator(data:List<Moderator?>?)
    }
}