package haina.ecommerce.view.forum.tab.myforum

import haina.ecommerce.base.BaseView
import haina.ecommerce.model.forum.*

interface MySubforumContract {

    interface View:BaseView{
        fun messageGetListSubforum(msg:String)
        fun messageGetListFollow(msg:String)
        fun getListSubforum(data:List<SubforumData?>?)
        fun getListSubforumFollow(data:List<SubforumData?>?)
    }

}