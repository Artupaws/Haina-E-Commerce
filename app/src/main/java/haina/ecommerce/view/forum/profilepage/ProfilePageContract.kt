package haina.ecommerce.view.forum.profilepage

import haina.ecommerce.base.BaseView
import haina.ecommerce.model.forum.DataAllThreads
import haina.ecommerce.model.forum.DataProfile
import haina.ecommerce.model.forum.SubforumEngagement

interface ProfilePageContract:BaseView {
    fun messageGiveUpvote(msg:String)
    fun getListPost(data: DataAllThreads)

    fun messageListPost(msg:String)
    fun getProfileData(data: DataProfile)

}