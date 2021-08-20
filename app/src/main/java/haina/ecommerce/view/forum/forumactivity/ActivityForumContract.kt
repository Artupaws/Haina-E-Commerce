package haina.ecommerce.view.forum.forumactivity

import haina.ecommerce.base.BaseView
import haina.ecommerce.model.forum.DataItemBan
import haina.ecommerce.model.forum.DataModList
import haina.ecommerce.model.forum.ModListItem

interface ActivityForumContract {

    interface View:BaseView{
        fun messageGetListMod(msg:String)
        fun messageGetListBan(msg:String)
        fun getDataListMod(data: List<ModListItem?>?)
        fun getDataListBan(data: List<DataItemBan?>?)
    }

}