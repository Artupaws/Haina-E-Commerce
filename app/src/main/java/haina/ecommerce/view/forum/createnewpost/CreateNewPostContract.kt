package haina.ecommerce.view.forum.createnewpost

import haina.ecommerce.base.BaseView
import haina.ecommerce.model.forum.DataSubforum
import haina.ecommerce.model.forum.DataSubforumHotPost
import haina.ecommerce.model.forum.SubforumData

interface CreateNewPostContract {

    interface View:BaseView{
        fun messageNewPost(msg:String)
        fun getListSubforum(data:List<SubforumData?>?)
        fun messageGetListSubforum(msg:String)
    }

}