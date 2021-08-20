package haina.ecommerce.view.forum.profileuser

import haina.ecommerce.base.BaseView
import haina.ecommerce.model.forum.DataProfile

interface ProfileUserContract {

    interface View:BaseView{
        fun messageGetProfile(msg:String)
        fun messageFollowSubforum(msg:String)
        fun getDataProfile(data:DataProfile)
    }

}