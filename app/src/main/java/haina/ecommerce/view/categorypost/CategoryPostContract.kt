package haina.ecommerce.view.categorypost

import haina.ecommerce.base.BaseView
import haina.ecommerce.model.categorypost.DataCategory

interface CategoryPostContract {

    interface View:BaseView{
        fun getListCategory(data:List<DataCategory?>?)
        fun messageGetCategory(msg:String)
    }

}