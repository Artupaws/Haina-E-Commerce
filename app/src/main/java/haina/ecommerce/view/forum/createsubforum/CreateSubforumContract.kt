package haina.ecommerce.view.forum.createsubforum

import haina.ecommerce.base.BaseView

interface CreateSubforumContract {

    interface View:BaseView{
        fun messageCreateSubforum(msg:String)
    }

}