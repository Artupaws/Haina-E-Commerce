package haina.ecommerce.view.forum.tab.profilecomment

import haina.ecommerce.base.BaseView
import haina.ecommerce.model.forum.*

interface ProfileCommentListContract {

    interface View: BaseView {
        fun messageGetComment(msg:String)

        fun getListComment(data: DataAllCommentUser)

    }
}