package haina.ecommerce.view.forum

import haina.ecommerce.base.BaseView
import haina.ecommerce.model.DataUser
import haina.ecommerce.model.forum.DataSearch

interface ForumFragmentContract {

    interface View:BaseView{
        fun messageGetSearch(msg:String)
        fun getDataSearch(data: DataSearch?)

        fun messageGetDataUSer(msg:String)
        fun getDataUser(data : DataUser?)
    }

}